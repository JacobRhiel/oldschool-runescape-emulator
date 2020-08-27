package rs.emulator.entity.actor.player.messages

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.entity.actor.player.widgets.WidgetEvent
import java.util.*

/**
 *
 * @author javatar
 */

interface IWidgetMessages : IMessages {

    fun sendClientScript(scriptId: Int, vararg params: Any)
    fun setWidgetText(widgetId: Int, defChildId: Int, text: String)
    fun sendCloseSub(parentId: Int, childId: Int)
    fun sendMoveSub(toParentId: Int, toChildId: Int, fromParentId: Int, fromChildId: Int)
    fun sendOpenOverlay(id: Int)
    fun sendOpenSub(parentId: Int, childId: Int, component: Int, interType: Int)
    fun sendDisplayWidgetUpdate()
    fun sendChatMessage(
        message: String,
        messageType: ChatMessageType = ChatMessageType.GAMEMESSAGE
    )

    fun sendUpdateFriendsList(friends: List<IPlayer>)

    fun sendAccessMask(widgetId: Int, defChildId: Int, cs2Children: IntRange, mask: Int)
        = sendAccessMask(widgetId, defChildId, cs2Children.first, cs2Children.last, mask)

    fun sendAccessMask(widgetId: Int, defChildId: Int, minCs2ChildId: Int, maxCs2ChildId: Int, mask: Int)

    fun sendAccessMask(widgetId: Int, defChildId: Int, cs2Children: IntRange, events: EnumSet<WidgetEvent>)
            = sendAccessMask(widgetId, defChildId, cs2Children.first, cs2Children.last, events)

    fun sendAccessMask(widgetId: Int, defChildId: Int, minCs2ChildId: Int, maxCs2ChildId: Int, events: EnumSet<WidgetEvent>)
    {
        val masks = events.sumBy { it.mask }
        return sendAccessMask(widgetId, defChildId, minCs2ChildId, maxCs2ChildId, masks)
    }

    fun sendAccessMask(widgetId: Int, defChildId: Int, cs2Children: IntRange, vararg events: WidgetEvent)
        = sendAccessMask(widgetId, defChildId, cs2Children.first, cs2Children.last, *events)

    fun sendAccessMask(widgetId: Int, defChildId: Int, minCs2ChildId: Int, maxCs2ChildId: Int, vararg events: WidgetEvent)
        = sendAccessMask(widgetId, defChildId, minCs2ChildId, maxCs2ChildId, EnumSet.copyOf(arrayOf(*events).asList()))

}