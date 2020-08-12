package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.IdleLogoutMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.IdleLogoutFactory

/**
 *
 * @author javatar
 */

class IdleLogoutListener : GamePacketListener<IdleLogoutMessage> {
    override fun handle(
        player: Player,
        message: IdleLogoutMessage
    ) {

        RSPluginManager.getExtensions(IdleLogoutFactory::class.java)
            .forEach {
                if (it.applicableToIdleLogout(player)) {
                    it.registerIdleLogoutAction().handleIdleLogout(player)
                }
            }

    }
}