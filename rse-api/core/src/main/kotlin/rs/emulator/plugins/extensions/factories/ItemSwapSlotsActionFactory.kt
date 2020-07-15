package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.ItemSwapSlotsAction

/**
 *
 * @author javatar
 */

interface ItemSwapSlotsActionFactory : ExtensionPoint {

    fun registerItemSwapWidget(widgetId: Int, widgetChildId: Int): ItemSwapSlotsAction

}