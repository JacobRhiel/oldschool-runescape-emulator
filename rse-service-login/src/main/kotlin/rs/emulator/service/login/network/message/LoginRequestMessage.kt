package rs.emulator.service.login.network.message

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.entity.player.Player
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.packet.session.PacketSession
import rs.emulator.service.login.LoginCredentials
import rs.emulator.service.login.LoginResult
import rs.emulator.service.login.worker.LoginWorker
import rs.emulator.service.login.worker.LoginWorkerService
import rs.emulator.world.repository.WorldRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chk
 */
data class LoginRequestMessage(val ctx: ChannelHandlerContext,
                               val channel: Channel,
                               val isaac: IntArray,
                               val credentials: LoginCredentials
) : KoinComponent, NetworkMessage
{

    private val loginService: LoginWorkerService = get()

    override fun handle(ctx: ChannelHandlerContext)
    {

        ctx.channel().attr(SESSION_KEY).set(PacketSession(ctx.channel(), isaac))

        val session = ctx.channel().attr(SESSION_KEY).get() as PacketSession

        var loginResult: LoginResult

        var isaac: IntArray = intArrayOf()

        val a = CompletableFuture<LoginResult>().completeAsync {

            val worker = LoginWorker(this@LoginRequestMessage)

            val loginResult = LoginResult(worker.execute())

            isaac = worker.request.isaac

            loginResult

        }

        loginResult = a.get(5, TimeUnit.SECONDS)

        ctx.channel().write(LoginResponseMessage(isaac, loginResult))

        val player = Player(ctx.channel())

        ctx.channel().attr(session.PLAYER_KEY).set(player)

        player.viewport.localPlayers[1] = player

        player.viewport.globalPlayers[1] = player

        WorldRepository.players.add(player)

        player.onLogin()

    }

}