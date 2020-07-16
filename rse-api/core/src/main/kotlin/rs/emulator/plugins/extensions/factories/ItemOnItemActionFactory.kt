package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.on.ItemOnItemAction

/**
 *
 * @author javatar
 */

interface ItemOnItemActionFactory : ExtensionPoint {

    fun registerItemOnItemAction(
        itemId: Int,
        slot: Int,
        widgetId: Int,
        widgetChildId: Int,
        selectedItemId: Int,
        selectedSlot: Int,
        selectedWidgetId: Int,
        selectedWidgetChildId: Int
    ): ItemOnItemAction

}