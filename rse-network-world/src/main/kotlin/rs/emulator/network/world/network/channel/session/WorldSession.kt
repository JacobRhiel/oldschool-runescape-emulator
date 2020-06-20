package rs.emulator.network.world.network.channel.session

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.session.NetworkSession
import rs.emulator.service.login.LoginCredentials
import rs.emulator.service.login.network.message.LoginRequestMessage
import rs.emulator.service.login.worker.LoginWorkerService

import kotlin.random.Random

/**
 *
 * @author Chk
 */
class WorldSession : KoinComponent, NetworkSession
{

    private val seed: Long = Random.nextLong()

    private val loginService: LoginWorkerService = get()

    internal var rsaBuffer: ByteBuf = Unpooled.EMPTY_BUFFER

    internal var isaacKeys: IntArray = IntArray(4)

    val credentials = LoginCredentials()

    fun fetchSeed() = seed

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {

        println("msg: " + msg)

        if(msg is LoginRequestMessage)
        {

            loginService.submit(msg)

            return

        }
        else msg.handle(ctx)

    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {

    }

}