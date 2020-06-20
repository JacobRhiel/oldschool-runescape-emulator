package rs.emulator.network.packet.session

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.session.NetworkSession

/**
 *
 * @author Chk
 */
class PacketSession(val channel: Channel,
                    val isaacKeys: IntArray
) : NetworkSession
{

    private val decodeRandom = IsaacRandom(isaacKeys)

    private val encodeRandom = IsaacRandom(IntArray(isaacKeys.size) { isaacKeys[it] + 50 })

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {



    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {



    }

}