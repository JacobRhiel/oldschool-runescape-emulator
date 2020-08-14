package rs.emulator.plugins.extensions.factories.actions.action

import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface ItemAction<T : Item> {

    fun handleItemAction(
        item : T,
        option : Int
    )

}