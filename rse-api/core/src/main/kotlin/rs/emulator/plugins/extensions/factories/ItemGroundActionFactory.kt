package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.action.ItemGroundAction

/**
 *
 * @author javatar
 */

interface ItemGroundActionFactory : ExtensionPoint {

    fun registerItemGroundAction(
        itemId: Int,
        x: Int,
        y: Int,
        option: Int,
        controlPressed: Boolean
    ): ItemGroundAction

}