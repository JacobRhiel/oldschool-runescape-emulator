package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface LogoutAction {

    fun onLogout(player : IPlayer)

}