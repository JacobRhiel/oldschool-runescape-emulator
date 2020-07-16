package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.action.ObjectAction

/**
 *
 * @author javatar
 */

interface ObjectActionFactory : ExtensionPoint {

    fun registerObjectActions(
        locId: Int,
        x: Int,
        y: Int,
        controlPressed: Boolean
    ): ObjectAction

}