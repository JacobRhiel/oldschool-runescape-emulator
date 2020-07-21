package rs.emulator.plugins.extensions.factories.actions.chat

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface TradeChatFilterAction {

    fun handleTradeChatFilter(player: IPlayer, tradeChatFilter: Int)

}