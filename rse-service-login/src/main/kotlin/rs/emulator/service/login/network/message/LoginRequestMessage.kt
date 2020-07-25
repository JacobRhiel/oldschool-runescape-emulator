package rs.emulator.service.login.network.message

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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
data class LoginRequestMessage(
    val ctx: ChannelHandlerContext,
    val channel: Channel,
    val isaac: IntArray,
    val credentials: LoginCredentials
) : KoinComponent, NetworkMessage {

    private val loginService: LoginWorkerService = get()

    override fun handle(ctx: ChannelHandlerContext) {
        val loginResult: LoginResult

        var isaac: IntArray = intArrayOf()

        val a = CompletableFuture<LoginResult>().completeAsync {

            val worker = LoginWorker(this@LoginRequestMessage)

            val loginResult = LoginResult(worker.execute())

            isaac = worker.request.isaac

            loginResult

        }

        loginResult = a.get(5, TimeUnit.SECONDS)

        ctx.channel().write(LoginResponseMessage(isaac, loginResult))

        val compositeDisposable = CompositeDisposable()

        ctx.channel().attr(SESSION_KEY).set(PacketSession(ctx.channel(), isaac, compositeDisposable))
        val session = ctx.channel().attr(SESSION_KEY).get() as PacketSession

        val player = Player(
            WorldRepository.nextPlayerIndex,
            ctx.channel(),
            session.outgoingPackets,
            credentials.username,
            compositeDisposable
        )

        player.add(ChannelCloseDisposable(ctx.channel()))

        player.viewport.localPlayers[player.index] = player

        player.viewport.globalPlayers[player.index] = player

        player.add(session.outgoingPackets
            .subscribe { ctx.channel().writeAndFlush(it) })
        player.add(session.incomingPackets
            .subscribe({
                val (metaData, gamePacket) = it
                metaData.handle(ctx.channel(), player, gamePacket)
            }, {
                it.printStackTrace()
            })
        )

        player.onLogin()

        //TODO - add player
        WorldRepository.players.add(player)

        if (ctx.channel().isActive)
            ctx.channel().flush()

    }

    class ChannelCloseDisposable(val channel: Channel) : Disposable {
        override fun isDisposed(): Boolean {
            return channel.isActive
        }

        override fun dispose() {
            channel.close()
        }

    }

}