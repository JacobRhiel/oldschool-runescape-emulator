package rs.emulator.plugins.extensions.factories.actions.chat

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface PublicChatFilterAction {

    fun handlePublicChatFilter(player: IPlayer, publicChatFilter: Int)

}