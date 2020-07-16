package rs.emulator.plugins.extensions.factories.actions.chat

/**
 *
 * @author javatar
 */

interface ChatTextFilterAction {

    fun checkChatText(text: String): Boolean

}