package rs.emulator.fileserver.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.fileserver.network.message.JS5CreateSessionMessage
import rs.emulator.fileserver.network.message.JS5HandshakeResponseMessage
import rs.emulator.fileserver.network.protocol.JS5ResponseProtocol
import rs.emulator.fileserver.network.session.JS5Session
import rs.emulator.network.SESSION_KEY
import rs.emulator.utilities.logger.info

/**
 *
 * @author Chk
 */
class JS5HandshakeDecoder
    : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        if (!buffer.isReadable || buffer.readableBytes() < 4) return

        ctx.channel().attr(SESSION_KEY).set(JS5Session())

        val currentRevision = 189//todo centralize

        println(currentRevision)

        val requestedRevision = buffer.readInt()

        if(requestedRevision != currentRevision)
        {

            info("Address {}, attempted to connect with an older client. Client Revision: {}", ctx.channel().remoteAddress(), requestedRevision)

            ctx.writeAndFlush(JS5HandshakeResponseMessage(JS5ResponseProtocol.OUT_OF_DATE))

        }

        ctx.pipeline().remove(this)

        out.add(JS5CreateSessionMessage())

    }

}