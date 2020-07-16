package rs.emulator.plugins.extensions.factories.actions.on

import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemOnNpcAction {

    fun handleItemOnNpc(player: IPlayer, npc: INpc, itemId: Int)

}