package rs.emulator.plugins.extensions

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.action.NpcAction

/**
 *
 * @author javatar
 */

fun Any.npcAction(block: (IPlayer, INpc, Int) -> Unit): NpcAction {
    return object : NpcAction {
        override fun handleNpcAction(player: IPlayer, npc: INpc, option: Int) {
            block(player, npc, option)
        }
    }
}