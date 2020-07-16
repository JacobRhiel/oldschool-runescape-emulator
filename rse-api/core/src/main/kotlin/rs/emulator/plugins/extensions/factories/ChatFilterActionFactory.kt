package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.chat.PrivateChatFilterAction
import rs.emulator.plugins.extensions.factories.actions.chat.PublicChatFilterAction
import rs.emulator.plugins.extensions.factories.actions.chat.TradeChatFilterAction

/**
 *
 * @author javatar
 */

interface ChatFilterActionFactory : ExtensionPoint {

    fun registerPublicChatFilterAction(
        publicChatFilter: Int
    ): PublicChatFilterAction

    fun registerPrivateChatFilterAction(
        privateChatFilter: Int
    ): PrivateChatFilterAction

    fun registerTradeChatFilterAction(
        tradeChatFilter: Int
    ): TradeChatFilterAction

}