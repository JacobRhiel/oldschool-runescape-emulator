package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.PlayerActions

/**
 *
 * @author javatar
 */

interface PlayerActionFactory : ExtensionPoint {

    fun registerPlayerActions(
        playerIndex: Int,
        option: Int,
        controlPressed: Boolean
    ): PlayerActions

}