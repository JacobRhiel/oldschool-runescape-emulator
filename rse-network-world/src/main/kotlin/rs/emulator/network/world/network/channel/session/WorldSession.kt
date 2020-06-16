package rs.emulator.network.world.network.channel.session

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.session.NetworkSession

import kotlin.random.Random

/**
 *
 * @author Chk
 */
class WorldSession : NetworkSession
{

    private val seed: Long = Random.nextLong()

    internal var rsaBuffer: ByteBuf = Unpooled.EMPTY_BUFFER

    internal var isaacKeys: IntArray = IntArray(4)

    fun fetchSeed() = seed

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {

    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {

    }

}