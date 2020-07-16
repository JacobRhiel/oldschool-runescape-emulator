package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.on.ItemOnNpcAction

/**
 *
 * @author javatar
 */

interface ItemOnNpcActionFactory : ExtensionPoint {

    fun registerItemOnNpcActions(
        npcIndex: Int,
        selectedItemId: Int,
        selectedItemSlot: Int,
        selectedWidgetId: Int,
        selectedWidgetChildId: Int,
        controlPressed: Boolean
    ): ItemOnNpcAction

}