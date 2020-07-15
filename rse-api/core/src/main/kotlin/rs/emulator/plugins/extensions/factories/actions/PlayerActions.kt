package rs.emulator.plugins.extensions.factories.actions

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface PlayerActions {

    fun handlePlayerAction(me: IPlayer, other: IPlayer, option: Int)

}