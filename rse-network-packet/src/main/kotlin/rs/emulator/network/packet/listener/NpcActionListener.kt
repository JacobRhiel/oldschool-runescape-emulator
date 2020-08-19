package rs.emulator.network.packet.listener

import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.flow.*
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.messages.IWidgetMessages
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.NpcActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.entity.actions.NpcActionFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get
import rs.emulator.world.GameWorld

/**
 *
 * @author javatar
 */

class NpcActionListener : GamePacketListener<NpcActionMessage> {
    override fun handle(
        player: Player,
        message: NpcActionMessage
    ) {
        if (message.option != -1 && message.npcIndex != -1) {
            val npc: INpc? = player.viewport.localNpcs[message.npcIndex]
            if (npc != null) {
                flowOf(*RSPluginManager.getExtensions<NpcActionFactory>().toTypedArray())
                    .onEmpty { player.messages().sendChatMessage("Nothing interesting happens.") }
                    .map { it.registerNpcActions(message.npcIndex, message.option, message.controlPressed) }
                    .onEach { it.handleNpcAction(player, npc, message.option) }
                    .launchIn(get<ActorScope>())
            }
        }
    }
}