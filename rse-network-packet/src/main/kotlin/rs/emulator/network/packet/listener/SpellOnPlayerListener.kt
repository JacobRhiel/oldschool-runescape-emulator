package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.SpellOnPlayerMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.SpellOnPlayerActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class SpellOnPlayerListener : GamePacketListener<SpellOnPlayerMessage> {
    override fun handle(channel: Channel, player: Player, message: SpellOnPlayerMessage) {

        //TODO - validate player exists

        RSPluginManager.getExtensions<SpellOnPlayerActionFactory>()
            .toObservable()
            .map {
                it.registerSpellOnPlayerAction(
                    message.playerIndex,
                    message.spellChildIndex,
                    message.spellComponentHash shr 16,
                    message.spellComponentHash and 255,
                    message.controlPressed
                )
            }.subscribe({
                val targetPlayer = GameWorld.players[message.playerIndex]
                val spell = GameWorld.spells[message.spellChildIndex]
                it.handleSpellOnPlayer(player, targetPlayer, spell)
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            }).dispose()

    }
}