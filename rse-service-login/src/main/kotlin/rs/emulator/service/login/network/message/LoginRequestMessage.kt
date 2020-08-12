package rs.emulator.service.login.network.message

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.entity.details.PlayerDetails
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
data class LoginRequestMessage(
    val ctx: ChannelHandlerContext,
    val channel: Channel,
    val isaac: IntArray,
    val credentials: LoginCredentials
) : KoinComponent, NetworkMessage {

    private val loginService: LoginWorkerService = get()

    @ExperimentalCoroutinesApi
    override fun handle(ctx: ChannelHandlerContext) {
        val loginResult: LoginResult
        val playerDetails: PlayerDetails

        var isaac: IntArray = intArrayOf()

        val a = CompletableFuture<Pair<LoginResult, PlayerDetails>>().completeAsync {

            val worker = LoginWorker(this@LoginRequestMessage)

            val pair = worker.execute()

            val loginResult = LoginResult(pair.first)

            isaac = worker.request.isaac

            loginResult to pair.second
        }

        val pair = a.get(5, TimeUnit.SECONDS)
        loginResult = pair.first
        playerDetails = pair.second

        ctx.channel().write(LoginResponseMessage(isaac, loginResult))

        val compositeDisposable = CompositeDisposable()

        ctx.channel().attr(SESSION_KEY).set(PacketSession(ctx.channel(), isaac, compositeDisposable))
        val session = ctx.channel().attr(SESSION_KEY).get() as PacketSession
        val player = Player(
            WorldRepository.nextPlayerIndex,
            session.outgoingPackets,
            session.incomingPacketChannel.openSubscription(),
            compositeDisposable,
            playerDetails
        )


        player.add(ChannelCloseDisposable(ctx.channel()))

        player.viewport.localPlayers[player.index] = player

        player.viewport.globalPlayers[player.index] = player

        player.add(session.outgoingPackets.subscribe { ctx.channel().writeAndFlush(it) })

        player.load()
        player.onLogin()

        //TODO - add player
        WorldRepository.players.add(player)

        if (ctx.channel().isActive)
            ctx.channel().flush()
    }

    class ChannelCloseDisposable(val channel: Channel) : Disposable {
        override fun isDisposed(): Boolean {
            return !channel.isActive
        }

        override fun dispose() {
            channel.close()
        }

    }

}