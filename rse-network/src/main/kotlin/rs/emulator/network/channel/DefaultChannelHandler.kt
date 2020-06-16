package rs.emulator.network.channel

import io.netty.channel.*
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
@ChannelHandler.Sharable class DefaultChannelHandler
    : ChannelHandlerAdapter()
{

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any)
    {

        val sessionAttached = ctx.channel().hasAttr(SESSION_KEY)

        if(!sessionAttached)
            println("No session attached to: $msg.")
        else if(msg is NetworkMessage)
        {

            val session = ctx.channel().attr(SESSION_KEY).get()

            session.onMessage(ctx, msg)

        }

    }

    override fun channelActive(ctx: ChannelHandlerContext?)
    {
        println("Channel active")
    }

    override fun channelInactive(ctx: ChannelHandlerContext?)
    {
        println("Channel inactive")
    }

    override fun channelRegistered(ctx: ChannelHandlerContext?)
    {
        println("channel registered")
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?)
    {
        println("channel unregistered")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable)
    {

        if(cause.localizedMessage == "An existing connection was forcibly closed by the remote host")
            println("Network forcibly closed by user")
        else ctx.fireExceptionCaught(cause)

    }

}