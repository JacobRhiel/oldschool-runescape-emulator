package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.ItemOnPlayerAction

/**
 *
 * @author javatar
 */

interface ItemOnPlayerActionFactory : ExtensionPoint {

    fun registerItemOnPlayerAction(
        targetPlayerIndex: Int,
        itemId: Int,
        widgetId: Int,
        widgetChildId: Int,
        selectedItemSlot: Int,
        controlPressed: Boolean
    ): ItemOnPlayerAction

}