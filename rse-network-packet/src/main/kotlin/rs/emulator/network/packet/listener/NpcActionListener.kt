package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import io.reactivex.rxkotlin.toObservable
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.NpcActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.NpcActionFactory
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class NpcActionListener : GamePacketListener<NpcActionMessage> {
    override fun handle(channel: Channel, player: Player, message: NpcActionMessage) {
        if (message.option != -1 && message.npcIndex != -1) {
            val npc: INpc? = GameWorld.npcs[message.npcIndex]
            if (npc != null) {
                RSPluginManager.getExtensions<NpcActionFactory>()
                    .toObservable()
                    .map {
                        it.registerNpcActions(
                            message.npcIndex,
                            message.option,
                            message.controlPressed
                        )
                    }
                    .subscribe({
                        it.handleNpcAction(
                            player,
                            npc,
                            message.option
                        )
                    }, {
                        player.messagesFromType<IWidgetMessages>()
                            .sendChatMessage("Nothing interesting happens.", 0)
                    })
                    .dispose()
            }
        }
    }
}