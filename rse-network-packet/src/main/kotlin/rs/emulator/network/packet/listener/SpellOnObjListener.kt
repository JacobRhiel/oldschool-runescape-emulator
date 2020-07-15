package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.SpellOnObjMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.SpellOnItemActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class SpellOnObjListener : GamePacketListener<SpellOnObjMessage> {
    override fun handle(channel: Channel, player: Player, message: SpellOnObjMessage) {

        //TODO - validate if interface is visible

        RSPluginManager.getExtensions<SpellOnItemActionFactory>()
            .toObservable()
            .map {
                it.registerSpellOnItemActions(
                    message.itemId,
                    message.slot,
                    message.componentHash shr 16,
                    message.componentHash and 255,
                    message.spellComponentHash shr 16,
                    message.spellComponentHash and 255,
                    message.spellChildIndex
                )
            }.subscribe({
                val spell = GameWorld.spells[message.spellChildIndex]
                it.handleSpellOnItem(
                    player,
                    spell,
                    message.itemId
                )
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            }).dispose()

    }
}