package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.actor.player.storage.inventory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ItemOnGroundItemMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ItemOnGroundItemActionFactory

/**
 *
 * @author javatar
 */

class ItemOnGroundItemListener : GamePacketListener<ItemOnGroundItemMessage> {
    override fun handle(channel: Channel, player: Player, message: ItemOnGroundItemMessage) {

        //TODO - validate item exists on ground

        if (player.containerManager().inventory()[message.selectedItemSlot].id == message.widgetItemId) {
            RSPluginManager.getExtensions(ItemOnGroundItemActionFactory::class.java)
                .forEach {
                    it.registerItemOnGroundItemAction().handleItemOnGroundItem(
                        player,
                        message.item,
                        message.selectedItemSlot,
                        message.groundItemId,
                        message.widgetItemId,
                        message.x,
                        message.y,
                        message.crlRun
                    )
                }
        }
    }
}