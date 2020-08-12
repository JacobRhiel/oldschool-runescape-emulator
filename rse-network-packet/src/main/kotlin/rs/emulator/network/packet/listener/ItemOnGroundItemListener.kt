package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjOnGroundObjMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.on.ItemOnGroundItemActionFactory

/**
 *
 * @author javatar
 */

class ItemOnGroundItemListener : GamePacketListener<ObjOnGroundObjMessage> {
    @ExperimentalCoroutinesApi
    override fun handle(
        player: Player,
        message: ObjOnGroundObjMessage
    ) {

        //TODO - validate item exists on ground

        if (player.inventory().elements[message.selectedItemSlot].id == message.widgetItemId) {

            RSPluginManager.getExtensions<ItemOnGroundItemActionFactory>()
                .toObservable()
                .map {
                    it.registerItemOnGroundItemAction(
                        message.item,
                        message.selectedItemSlot,
                        message.groundItemId,
                        message.widgetItemId,
                        message.x,
                        message.y,
                        message.controlPressed
                    )
                }
                .subscribe({
                    it.handleItemOnGroundItem(
                        player,
                        message.item,
                        message.groundItemId
                    )
                }, {
                    player.messagesFromType<IWidgetMessages>()
                        .sendChatMessage("Nothing interesting happens.", 0)
                })
                .dispose()
        }
    }
}