package rs.emulator.plugins.extensions.factories.entity.chat

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface ChatFilterAction {

    fun handleChatFilterSettings(
        player : IPlayer,
        publicChatFilter: Int,
        privateChatFilter: Int,
        tradeChatFilter: Int
    ) : Boolean

}