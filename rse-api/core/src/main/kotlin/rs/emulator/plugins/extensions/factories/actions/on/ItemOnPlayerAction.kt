package rs.emulator.plugins.extensions.factories.actions.on

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ItemOnPlayerAction {

    fun handleItemOnPlayer(player: IPlayer, targetPlayer: IPlayer, itemId: Int)

}