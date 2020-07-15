package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.ItemOnGroundItemAction

/**
 *
 * @author javatar
 */

interface ItemOnGroundItemActionFactory : ExtensionPoint {

    fun registerItemOnGroundItemAction(): ItemOnGroundItemAction

}