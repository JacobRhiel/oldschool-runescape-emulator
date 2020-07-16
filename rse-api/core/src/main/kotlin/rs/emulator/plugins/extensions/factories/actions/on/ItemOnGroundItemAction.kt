package rs.emulator.plugins.extensions.factories.actions.on

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemOnGroundItemAction {

    fun handleItemOnGroundItem(
        player: IPlayer,
        item: Int,
        groundItemId: Int
    )

}