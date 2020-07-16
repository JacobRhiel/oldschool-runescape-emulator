package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.SpellOnGroundObjMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.spells.SpellOnGroundItemActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class SpellOnGroundObjListener : GamePacketListener<SpellOnGroundObjMessage> {
    override fun handle(channel: Channel, player: Player, message: SpellOnGroundObjMessage) {

        //TODO - validate item exists at x and y

        RSPluginManager.getExtensions<SpellOnGroundItemActionFactory>()
            .toObservable()
            .map {
                it.registerSpellOnGroundItemActions(
                    message.itemId,
                    message.x,
                    message.y,
                    message.spellChildIndex,
                    message.spellComponentHash shr 16,
                    message.spellComponentHash and 255,
                    message.controlPressed
                )
            }.subscribe({
                val spell = GameWorld.spells[message.spellChildIndex]
                it.handleSpellOnGroundItem(
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