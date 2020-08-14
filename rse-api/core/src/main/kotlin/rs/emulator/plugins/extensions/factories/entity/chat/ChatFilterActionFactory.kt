package rs.emulator.plugins.extensions.factories.entity.chat

import org.pf4j.ExtensionPoint

/**
 *
 * @author javatar
 */

interface ChatFilterActionFactory : ExtensionPoint {

    fun registerPublicChatFilter(
        publicChatFilter: Int,
        privateChatFilter: Int,
        tradeChatFilter: Int
    ) : ChatFilterAction

}