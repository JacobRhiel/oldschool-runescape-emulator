package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface NpcActions {

    fun handleNpcAction(player: IPlayer, npc: INpc, option: Int)

}