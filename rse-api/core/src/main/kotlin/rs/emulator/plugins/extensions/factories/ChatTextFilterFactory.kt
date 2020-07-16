package rs.emulator.plugins.extensions.factories

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.actions.chat.ChatTextFilterAction

/**
 *
 * @author javatar
 */

interface ChatTextFilterFactory : ExtensionPoint {

    fun registerChatTextFilter(
        chatType: Int,
        chatEffect: Int,
        chatColor: Int
    ): ChatTextFilterAction

}