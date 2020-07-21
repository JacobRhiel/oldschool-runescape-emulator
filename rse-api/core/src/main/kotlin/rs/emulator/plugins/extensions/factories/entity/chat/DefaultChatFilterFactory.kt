package rs.emulator.plugins.extensions.factories.entity.chat

import rs.emulator.plugins.extensions.factories.actions.chat.ChatTextFilterAction

/**
 *
 * @author javatar
 */

class DefaultChatFilterFactory : ChatTextFilterFactory, ChatTextFilterAction {
    override fun checkChatText(text: String): Boolean {
        return true
    }

    override fun registerChatTextFilter(chatType: Int, chatEffect: Int, chatColor: Int): ChatTextFilterAction {
        return this
    }
}