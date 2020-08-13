package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.LogoutAction

/**
 *
 * @author javatar
 */

interface LogoutActionFactory : ExtensionPoint {

    fun registerLogoutAction(
        player : IPlayer
    ) : LogoutAction

}