package rs.emulator.plugins.extensions.factories.actions.idle

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface IdleMouseTickAction {

    fun handleIdleMouse(player: IPlayer)

}