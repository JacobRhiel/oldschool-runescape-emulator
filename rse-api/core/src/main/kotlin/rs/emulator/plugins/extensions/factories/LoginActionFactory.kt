package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.LoginAction

/**
 *
 * @author javatar
 */

interface LoginActionFactory : ExtensionPoint {

    fun registerLoginAction(
        player: IPlayer
    ): LoginAction

}