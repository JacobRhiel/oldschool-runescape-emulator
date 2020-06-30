package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.bouncycastle.crypto.engines.XTEAEngine
import org.bouncycastle.crypto.params.KeyParameter
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.buffer.BufferUtils.readJagexString
import rs.emulator.buffer.BufferUtils.readString
import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.buffer.reader.BufferedReader
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.encryption.xtea.XTEA
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.world.network.channel.message.WorldHandshakeResponseMessage
import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol
import rs.emulator.network.world.network.channel.session.WorldSession
import rs.emulator.service.login.network.encoder.LoginResponseEncoder
import rs.emulator.service.login.network.message.LoginRequestMessage
import java.nio.ByteBuffer

/**
 *
 * @author Chk
 */
class WorldIsaacEncryptionDecoder
    : KoinComponent, ByteToMessageDecoder()
{

    private val fileStore: VirtualFileStore = get()

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is WorldSession)
            throw Error("Session is not WorldSession in WorldIsaaacEncryptionDecoder.")

        val keys = session.isaacKeys

        val isaacBuffer = buffer.decipher(keys)

        val username = isaacBuffer.readString()

        session.credentials.username = username

        val clientSettings = isaacBuffer.readByte().toInt()

        val clientResizable = (clientSettings shr 1) == 1

        val lowDetail = (clientSettings and 255) == 1

        val clientWidth = isaacBuffer.readUnsignedShort()

        val clientHeight = isaacBuffer.readUnsignedShort()

        isaacBuffer.skipBytes(24) // random.dat data

        val unknown = isaacBuffer.readString()

        isaacBuffer.skipBytes(Int.SIZE_BYTES)

        isaacBuffer.skipBytes(53)

        isaacBuffer.readByte()//client type

        isaacBuffer.readInt()//always 0

        val crcHashes = fileStore.fetchIndexCrcHashes()

        val reader = BufferedReader(isaacBuffer)

        val crcOrder = intArrayOf(6, 2, 11, 7, 8, 12, 19, 13, 18, 14, 17, 9, 5, 1, 20, 16, 15, 10, 3, 4, 0)

        val requestedCrcHashes = IntArray(crcHashes.size)

        crcOrder.forEach { idx ->

            requestedCrcHashes[idx] = when(idx)
            {

                6, 7, 19, 14 -> reader.getSigned(DataType.INT, DataOrder.INVERSED_MIDDLE)

                2, 13, 17, 5, 20, 10, 3, 0, 16 -> reader.getSigned(DataType.INT, DataOrder.LITTLE)

                11, 8, 12, 18, 9, 1 -> reader.getSigned(DataType.INT, DataOrder.MIDDLE)

                15, 4 -> reader.getSigned(DataType.INT)

                else -> -1

            }.toInt()

        }

        for (i in requestedCrcHashes.indices)
        {

            /**
             * CRC for index 16 is always sent as 0 (at least on the
             * Desktop client, need to look into mobile).
             */
            if (i == 16) {
                continue
            }
            if (crcHashes[i] != requestedCrcHashes[i])
            {
                buffer.resetReaderIndex()
                buffer.skipBytes(buffer.readableBytes())
                //info { "User '$username' login request crc mismatch [requestCrc=${requestedCrcHashes.contentToString()}, cacheCrc=${crcHashes.contentToString()}]." }
                ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.REVISION_MISMATCH))

                return
            }
        }

        ctx.pipeline().addBefore(DefaultChannelHandler::class.simpleName, LoginResponseEncoder::class.simpleName, LoginResponseEncoder())

        val request = LoginRequestMessage(ctx, ctx.channel(), session.isaacKeys, session.credentials)

        out.add(request)

        ctx.pipeline().remove(this)

    }

    private fun ByteBuf.decipher(isaacKeys: IntArray): ByteBuf
    {

        val data = ByteArray(readableBytes())

        readBytes(data)

        return Unpooled.wrappedBuffer(XTEA.decipher(isaacKeys, data, 0, data.size))

    }

}