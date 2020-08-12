package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjOnPlayerMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.on.ItemOnPlayerActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class ItemOnPlayerListener : GamePacketListener<ObjOnPlayerMessage> {
    override fun handle(
        player: Player,
        message: ObjOnPlayerMessage
    ) {

        //TODO - validate interface is visible and items exists

        RSPluginManager.getExtensions<ItemOnPlayerActionFactory>()
            .toObservable()
            .map {
                it.registerItemOnPlayerAction(
                    message.targetPlayerIndex,
                    message.itemId,
                    message.componentHash shr 16,
                    message.componentHash and 255,
                    message.selectedItemSlot,
                    message.controlPressed
                )
            }.subscribe({
                val targetPlayer: IPlayer? = GameWorld.players[message.targetPlayerIndex]
                if (targetPlayer != null) {
                    it.handleItemOnPlayer(
                        player,
                        targetPlayer,
                        message.itemId
                    )
                }
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            }).dispose()

    }
}