package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemSwapSlotsAction {

    fun handleItemSwap(player: IPlayer, destinationSlot: Int, sourceSlot: Int)

}