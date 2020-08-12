package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjGroundActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.ItemGroundActionFactory

/**
 *
 * @author javatar
 */

class ObjGroundActionListener : GamePacketListener<ObjGroundActionMessage> {
    override fun handle(
        player: Player,
        message: ObjGroundActionMessage
    ) {

        //TODO - validate if item actually exists

        if (message.option != -1) {
            RSPluginManager
                .getExtensions<ItemGroundActionFactory>()
                .toObservable()
                .map {
                    it.registerItemGroundAction(
                        message.itemId,
                        message.x,
                        message.y,
                        message.option,
                        message.controlPressed
                    )
                }
                .subscribe({
                    it.handleItemGroundAction(
                        message.itemId,
                        message.option
                    )
                }, {
                    player.messagesFromType<IWidgetMessages>()
                        .sendChatMessage("Nothing interesting happens.", 0)
                }).dispose()
        }

    }
}