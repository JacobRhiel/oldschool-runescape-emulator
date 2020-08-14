package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.material.items.Item
import rs.emulator.plugins.extensions.factories.actions.action.ItemAction

/**
 *
 * @author javatar
 */

interface ItemActionFactory : ExtensionPoint {

    fun registerItemAction(
        itemId : Int,
        option : Int,
        interfaceId : Int,
        componentId : Int
    ) : ItemAction<in Item>

}