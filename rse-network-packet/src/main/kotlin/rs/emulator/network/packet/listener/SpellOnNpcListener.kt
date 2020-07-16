package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.SpellOnNpcMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.SpellOnNpcActionsFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class SpellOnNpcListener : GamePacketListener<SpellOnNpcMessage> {
    override fun handle(channel: Channel, player: Player, message: SpellOnNpcMessage) {

        //TODO - validate npc exists

        RSPluginManager.getExtensions<SpellOnNpcActionsFactory>()
            .toObservable()
            .map {
                it.registerSpellOnNpcActions(
                    message.npcIndex,
                    message.spellChildIndex,
                    message.spellComponentHash shr 16,
                    message.spellComponentHash and 255,
                    message.controlPressed
                )
            }.subscribe({
                val spell = GameWorld.spells[message.spellChildIndex]
                val npc = GameWorld.npcs[message.npcIndex]
                it.handleSpellOnNpc(player, npc, spell)
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            })

    }
}