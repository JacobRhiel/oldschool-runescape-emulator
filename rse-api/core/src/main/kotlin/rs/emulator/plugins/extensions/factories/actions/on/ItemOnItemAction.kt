package rs.emulator.plugins.extensions.factories.actions.on

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemOnItemAction {

    fun handleItemOnItem(
        player: IPlayer,
        itemId: Int,
        selectedItemId: Int
    )

}