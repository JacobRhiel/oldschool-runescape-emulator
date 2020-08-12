package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MouseClickMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.IntractableItemExtensionPoint

/**
 *
 * @author Chk
 */
class MouseClickListener : GamePacketListener<MouseClickMessage>
{

    override fun handle(
        player: Player,
        message: MouseClickMessage
    )
    {

        val extensions = RSPluginManager.getExtensions(IntractableItemExtensionPoint::class.java)

        extensions.forEach {
            if (it.id == message.button) {
                it.apply {
                    player.interact { true }
                }
            }
        }

    }

}