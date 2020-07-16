package rs.emulator.plugins.extensions.factories.on

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.on.ItemOnGroundItemAction

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