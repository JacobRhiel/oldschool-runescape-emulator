package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.SpellOnLocMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.SpellOnObjectActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class SpellOnLocListener : GamePacketListener<SpellOnLocMessage> {
    override fun handle(channel: Channel, player: Player, message: SpellOnLocMessage) {

        //TODO - validate object exists at this location

        RSPluginManager.getExtensions<SpellOnObjectActionFactory>()
            .toObservable()
            .map {
                it.registerSpellOnObjectActions(
                    message.locId,
                    message.x,
                    message.y,
                    message.componentHash shr 16,
                    message.componentHash and 255,
                    message.spellChildIndex,
                    message.controlPressed
                )
            }.subscribe({
                val obj = GameWorld.objects.find { loc -> loc.id == message.locId }
                if (obj != null) {
                    val spell = GameWorld.spells[message.spellChildIndex]
                    it.handleSpellOnObject(player, obj, spell)
                }
            }, {
                player.messagesFromType<IWidgetMessages>()
                    .sendChatMessage("Nothing interesting happens.")
            }).dispose()

    }
}