package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.LegacyObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.LegacyItemActionFactory

/**
 *
 * @author javatar
 */

class LegacyObjActionListener : GamePacketListener<LegacyObjActionMessage> {
    override fun handle(channel: Channel, player: Player, message: LegacyObjActionMessage) {

        //TODO - Validate interface is visible

        RSPluginManager.getExtensions<LegacyItemActionFactory>()
            .toObservable()
            .map {
                it.registerLegacyItemActions(
                    message.itemId,
                    message.slot,
                    message.componentHash shr 16,
                    message.componentHash and 255
                )
            }.subscribe({
                it.handleLegacyItemAction(player, message.itemId)
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            })

    }
}