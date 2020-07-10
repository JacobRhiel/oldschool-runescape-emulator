package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.applyEach
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.ItemActionExtensionPoint

/**
 *
 * @author Chk
 */
class ObjActionListener : GamePacketListener<ObjActionMessage> {
    override fun handle(channel: Channel, player: Player, message: ObjActionMessage) {

        println("[ObjAction] - ${message.item}, ${message.option}, ${message.componentHash}")

        RSPluginManager.getExtensions(ItemActionExtensionPoint::class.java).applyEach {
            player.interact(
                message.item,
                message.option,
                message.componentHash shr 16,
                message.componentHash and 255
            )
        }
    }
}