package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.ItemOnGroundItemAction

/**
 *
 * @author javatar
 */

interface ItemOnGroundItemActionFactory : ExtensionPoint {

    fun registerItemOnGroundItemAction(
        itemId: Int,
        slot: Int,
        groundItemId: Int,
        widgetItemId: Int,
        x: Int,
        y: Int,
        controlPressed: Boolean
    ): ItemOnGroundItemAction

}