package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjOnNpcMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ItemOnNpcActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class ObjOnNpcListener : GamePacketListener<ObjOnNpcMessage> {
    override fun handle(channel: Channel, player: Player, message: ObjOnNpcMessage) {

        //TODO - validate if npc exists

        RSPluginManager.getExtensions<ItemOnNpcActionFactory>()
            .toObservable()
            .map {
                it.registerItemOnNpcActions(
                    message.npcIndex,
                    message.selectedItemId,
                    message.selectedItemSlot,
                    message.selectedComponentHash shr 16,
                    message.selectedComponentHash and 255,
                    message.controlPressed
                )
            }.subscribe({
                val npc = GameWorld.npcs[message.npcIndex]
                it.handleItemOnNpc(player, npc, message.selectedItemId)
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            }).dispose()

    }
}