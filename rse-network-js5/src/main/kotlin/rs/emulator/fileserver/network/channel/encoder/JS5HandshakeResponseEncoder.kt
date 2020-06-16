package rs.emulator.fileserver.network.channel.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.fileserver.network.message.JS5HandshakeResponseMessage
import rs.emulator.fileserver.network.protocol.JS5ResponseProtocol

/**
 *
 * @author Chk
 */
class JS5HandshakeResponseEncoder
    : MessageToByteEncoder<JS5HandshakeResponseMessage>()
{

    override fun encode(ctx: ChannelHandlerContext, msg: JS5HandshakeResponseMessage, buffer: ByteBuf)
    {

        val response = msg.response

        buffer.writeByte(response.opcode)

        if(response == JS5ResponseProtocol.OUT_OF_DATE)
            ctx.channel().closeFuture()

    }

}