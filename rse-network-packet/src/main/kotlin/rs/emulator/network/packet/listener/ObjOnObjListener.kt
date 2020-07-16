package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjOnObjMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ItemOnItemActionFactory

/**
 *
 * @author javatar
 */

class ObjOnObjListener : GamePacketListener<ObjOnObjMessage> {
    override fun handle(channel: Channel, player: Player, message: ObjOnObjMessage) {

        //TODO - validate items exist in both widget containers

        RSPluginManager.getExtensions<ItemOnItemActionFactory>()
            .toObservable()
            .map {
                it.registerItemOnItemAction(
                    message.itemId,
                    message.slot,
                    message.componentHash shr 16,
                    message.componentHash and 255,
                    message.selectedItemId,
                    message.selectedSlot,
                    message.selectedComponentHash shr 16,
                    message.selectedComponentHash and 255
                )
            }.subscribe({
                it.handleItemOnItem(player, message.itemId, message.selectedItemId)
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            })

    }
}