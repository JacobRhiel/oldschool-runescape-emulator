package rs.emulator.network.packet.listener

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IdleLogoutMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.IdleLogoutFactory
import rs.emulator.reactive.launch

/**
 *
 * @author javatar
 */

class IdleLogoutListener : GamePacketListener<IdleLogoutMessage> {
    override fun handle(
        player: Player,
        message: IdleLogoutMessage
    ) {
        flowOf(*RSPluginManager.getExtensions<IdleLogoutFactory>().toTypedArray())
            .filter { it.applicableToIdleLogout(player) }
            .map { it.registerIdleLogoutAction() }
            .onEach { it.handleIdleLogout(player) }
            .launch()
    }
}