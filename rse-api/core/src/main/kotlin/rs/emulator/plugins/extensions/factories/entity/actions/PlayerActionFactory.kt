package rs.emulator.plugins.extensions.factories.entity.actions

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.action.PlayerAction

/**
 *
 * @author javatar
 */

interface PlayerActionFactory : ExtensionPoint {

    fun registerPlayerActions(
        playerIndex: Int,
        option: Int,
        controlPressed: Boolean
    ): PlayerAction

}