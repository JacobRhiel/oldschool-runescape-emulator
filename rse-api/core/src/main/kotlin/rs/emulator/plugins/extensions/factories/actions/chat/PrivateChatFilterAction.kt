package rs.emulator.plugins.extensions.factories.actions.chat

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface PrivateChatFilterAction {

    fun handlePrivateChatFilter(player: IPlayer, privateChatFilter: Int)

}