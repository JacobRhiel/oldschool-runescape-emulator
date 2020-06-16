package rs.emulator.network.session

import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
class DefaultSession : NetworkSession
{

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {

        println("msg received: $msg")

        msg.handle(ctx)

    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {
        //todo
    }

}