package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.MouseClickMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.IntractableItemExtensionPoint

/**
 *
 * @author Chk
 */
class MouseClickListener : GamePacketListener<MouseClickMessage>
{

    override fun handle(channel: Channel, player: Player, message: MouseClickMessage)
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