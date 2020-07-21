package rs.emulator.plugins.extensions.factories.actions.action

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface LegacyItemAction {

    fun handleLegacyItemAction(player: IPlayer, itemId: Int)

}