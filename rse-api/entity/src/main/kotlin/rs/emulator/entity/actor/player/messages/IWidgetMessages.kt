package rs.emulator.entity.actor.player.messages

import rs.emulator.entity.actor.player.messages.chat.ChatMessageType

/**
 *
 * @author javatar
 */

interface IWidgetMessages : IMessages {

    fun sendClientScript(scriptId: Int, vararg params: Any)
    fun setWidgetText(widgetId: Int, defChildId: Int, text: String)
    fun sendOpenOverlay(id: Int)
    fun sendOpenSub(parentId: Int, childId: Int, component: Int, interType: Int)
    fun sendDisplayWidgetUpdate()
    fun sendChatMessage(message: String, messageType: ChatMessageType = ChatMessageType.GAMEMESSAGE) //TODO - refactor message type to enum
    fun sendAccessMask(widgetId: Int, defChildId: Int, minCs2ChildId: Int, maxCs2ChildId: Int, mask: Int)

}