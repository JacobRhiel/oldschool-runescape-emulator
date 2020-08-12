package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IdleMouseTickMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.IdleMouseTickActionFactory

/**
 *
 * @author javatar
 */

class IdleMouseTickListener : GamePacketListener<IdleMouseTickMessage> {
    override fun handle(
        player: Player,
        message: IdleMouseTickMessage
    ) {

        RSPluginManager.getExtensions<IdleMouseTickActionFactory>()
            .toObservable()
            .map {
                it.registerIdleMouseTickAction(
                    player.idleMouseTicks.getAndIncrement()
                )
            }.subscribe({
                it.handleIdleMouse(player)
            }, {
                it.printStackTrace()
            }).dispose()

    }
}