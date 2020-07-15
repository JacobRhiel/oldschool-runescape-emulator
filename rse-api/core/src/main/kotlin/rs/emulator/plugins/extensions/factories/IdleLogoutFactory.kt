package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.extensions.factories.actions.IdleLogoutAction

/**
 *
 * @author javatar
 */

interface IdleLogoutFactory : ExtensionPoint {

    fun registerIdleLogoutAction(): IdleLogoutAction

    fun applicableToIdleLogout(player: IPlayer): Boolean

}