package rs.emulator.network.session

import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
interface NetworkSession
{

    fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)

    fun onDestroy(ctx: ChannelHandlerContext)

}