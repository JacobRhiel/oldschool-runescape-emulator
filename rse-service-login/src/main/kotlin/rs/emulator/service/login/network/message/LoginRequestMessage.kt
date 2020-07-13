package rs.emulator.service.login.network.message

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.reactivex.rxkotlin.ofType
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.entity.player.Player
import rs.emulator.entity.update.task.UpdateSynchronizationTask
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.packet.atest.UpdatePlayerSyncMessage
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.packet.session.PacketSession
import rs.emulator.packet.api.GamePacket
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

        ctx.channel().attr(SESSION_KEY).set(PacketSession(ctx.channel(), isaac))

        val session = ctx.channel().attr(SESSION_KEY).get() as PacketSession

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

        val player = Player(ctx.channel(), session.outgoingPackets)

        ctx.channel().attr(session.PLAYER_KEY).set(player)

        player.viewport.localPlayers[1] = player

        player.viewport.globalPlayers[1] = player

        session.outgoingPackets
            .subscribe {
                if(it is UpdatePlayerSyncMessage<*>) {
                    ctx.channel().writeAndFlush(it)
                } else {
                    ctx.channel().write(it)
                }
            }


        session.incomingPackets.subscribe {
            val (metaData, gamePacket) = it
            metaData.handle(ctx.channel(), player, gamePacket)
        }

        WorldRepository.players.add(player)

        player.onLogin()

    }

}