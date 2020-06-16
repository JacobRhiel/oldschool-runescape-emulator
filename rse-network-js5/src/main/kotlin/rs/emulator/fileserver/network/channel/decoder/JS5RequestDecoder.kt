package rs.emulator.fileserver.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.fileserver.network.message.JS5FileRequestMessage
import rs.emulator.fileserver.network.message.JS5SetupXORMessage
import rs.emulator.fileserver.network.protocol.JS5RequestProtocol
import rs.emulator.fileserver.network.protocol.JS5RequestProtocol.*
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
class JS5RequestDecoder
    : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        if (!buffer.isReadable || buffer.readableBytes() < 4) return

        val opcode: Int = buffer.readByte().toInt()

        val protocol = JS5RequestProtocol.fetchProtocol(opcode) ?: throw IllegalArgumentException("Invalid JS5 request: $opcode")

        var msg: NetworkMessage? = null

        when(protocol)
        {

            REQUEST_PREFETCH -> msg = JS5FileRequestMessage(false, buffer.readUnsignedByte().toInt(), buffer.readUnsignedShort())

            REQUEST_URGENT -> msg = JS5FileRequestMessage(true, buffer.readUnsignedByte().toInt(), buffer.readUnsignedShort())

            LOGIN_NOTIFY     -> buffer.readMedium()

            LOGOUT_NOTIFY    -> buffer.readMedium()

            SETUP_XOR        ->
            {

                msg = JS5SetupXORMessage(buffer.readByte().toInt())

                //throw away 0 value trailer.
                buffer.readShort()

            }

        }

        if(msg != null)
            out.add(msg)

    }

}
