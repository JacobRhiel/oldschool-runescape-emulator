package rs.emulator.plugins.extensions.factories.on

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.on.ItemOnPlayerAction

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