package rs.emulator.plugins.extensions.factories.actions.action

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface PlayerAction {

    fun handlePlayerAction(me: IPlayer, other: IPlayer, option: Int)

}