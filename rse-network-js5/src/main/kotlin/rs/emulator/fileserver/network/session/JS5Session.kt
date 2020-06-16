package rs.emulator.fileserver.network.session

import io.netty.channel.ChannelHandlerContext
import rs.emulator.fileserver.network.message.JS5SetupXORMessage
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.session.NetworkSession

/**
 *
 * @author Chk
 */
class JS5Session
    : NetworkSession
{

    var xorKey: Int = 0

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {

        when (msg)
        {

            is JS5SetupXORMessage -> xorKey = msg.key

            else              ->
            {

                println("handling msg: $msg")

                msg.handle(ctx)

            }

        }

    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {

        xorKey = -1

        ctx.channel().closeFuture()

    }

}