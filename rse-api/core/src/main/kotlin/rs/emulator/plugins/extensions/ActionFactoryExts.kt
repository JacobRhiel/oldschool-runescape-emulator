package rs.emulator.plugins.extensions

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.NpcActions

/**
 *
 * @author javatar
 */

fun Any.npcAction(block: (IPlayer, INpc, Int) -> Unit): NpcActions {
    return object : NpcActions {
        override fun handleNpcAction(player: IPlayer, npc: INpc, option: Int) {
            block(player, npc, option)
        }
    }
}