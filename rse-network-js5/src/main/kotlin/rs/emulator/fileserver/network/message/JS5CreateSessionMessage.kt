package rs.emulator.fileserver.network.message

import io.netty.channel.ChannelHandlerContext
import rs.emulator.fileserver.network.channel.decoder.JS5RequestDecoder
import rs.emulator.fileserver.network.channel.encoder.JS5FileEncoder
import rs.emulator.fileserver.network.channel.encoder.JS5XOREncoder
import rs.emulator.fileserver.network.protocol.JS5ResponseProtocol
import rs.emulator.fileserver.network.session.JS5Session
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
class JS5CreateSessionMessage : NetworkMessage
{

    override fun handle(ctx: ChannelHandlerContext)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is JS5Session)
            throw Error("This shouldn't happen. invalid JS5Session")

        ctx.pipeline().addBefore(DefaultChannelHandler::class.simpleName, JS5FileEncoder::class.simpleName, JS5FileEncoder())

        ctx.pipeline().addBefore(DefaultChannelHandler::class.simpleName, JS5XOREncoder::class.simpleName, JS5XOREncoder())

        ctx.pipeline().addBefore(DefaultChannelHandler::class.simpleName, JS5RequestDecoder::class.simpleName, JS5RequestDecoder())

        ctx.writeAndFlush(JS5HandshakeResponseMessage(JS5ResponseProtocol.OK))

    }

}
