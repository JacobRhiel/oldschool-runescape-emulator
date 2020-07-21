package rs.emulator.plugins.extensions.factories.entity.actions

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.action.LegacyItemAction

/**
 *
 * @author javatar
 */

interface LegacyItemActionFactory : ExtensionPoint {

    fun registerLegacyItemActions(
        itemId: Int,
        slot: Int,
        widgetId: Int,
        widgetChildId: Int
    ): LegacyItemAction

}