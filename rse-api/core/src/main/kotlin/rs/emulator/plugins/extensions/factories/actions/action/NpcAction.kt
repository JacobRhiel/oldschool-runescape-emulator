package rs.emulator.plugins.extensions.factories.actions.action

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface NpcAction {

    fun handleNpcAction(player: IPlayer, npc: INpc, option: Int)

}