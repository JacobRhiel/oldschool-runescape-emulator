package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.rsa.RSAService
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.world.network.channel.message.WorldHandshakeResponseMessage
import rs.emulator.network.world.network.channel.protocol.WorldConnectionRequestProtocol
import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol
import rs.emulator.network.world.network.channel.session.WorldSession
import rs.emulator.utilities.logger.info
import java.math.BigInteger

/**
 *
 * @author Chk
 */
class WorldRSAEncryptionDecoder(
    private val protocol: WorldConnectionRequestProtocol
)
    : KoinComponent, ByteToMessageDecoder()
{

    private val rsaService: RSAService = get()

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is WorldSession)
            throw Error("Not world session in RSADecoder.")

        val modulus = rsaService.modulus

        val exponent = rsaService.exponent

        val secureBufLength = buffer.readUnsignedShort()

        val buf = buffer.readBytes(secureBufLength)

        val rsaValue = BigInteger(buf.toByteArraySafe()).modPow(exponent, modulus)

        val rsaBuffer = Unpooled.wrappedBuffer(rsaValue.toByteArray())

        val successfulEncryption = rsaBuffer.readUnsignedByte().toInt() == 1

        if (!successfulEncryption)
        {

            rsaBuffer.skipBytes(rsaBuffer.readableBytes())//todo check..

            info("Channel '{}' login request rejected.", ctx.channel())

            ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.BAD_SESSION_ID))

            return
        }

        var isaacKeys = IntArray(4) { rsaBuffer.readInt() }

        val clientSeed = rsaBuffer.readLong()

        if(protocol == WorldConnectionRequestProtocol.RECONNECTION)
            isaacKeys = IntArray(4) { rsaBuffer.readInt() }

        if(clientSeed != session.fetchSeed())
        {

            info("User '{}' login request seed mismatch [receivedSeed=$clientSeed, expectedSeed=${session.fetchSeed()}].", clientSeed)

            ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.BAD_SESSION_ID)).addListener(
                ChannelFutureListener.CLOSE)

        }

        session.isaacKeys = isaacKeys

        session.rsaBuffer = rsaBuffer

        ctx.pipeline().remove(this)

    }

    private fun ByteBuf.toByteArraySafe(): ByteArray
    {

        if (this.hasArray())
            return this.array()

        val bytes = ByteArray(this.readableBytes())

        this.getBytes(this.readerIndex(), bytes)

        return bytes

    }

}