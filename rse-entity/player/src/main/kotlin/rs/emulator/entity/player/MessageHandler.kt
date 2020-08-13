package rs.emulator.entity.player

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.network.packet.message.outgoing.*

/**
 *
 * @author javatar
 */
@ExperimentalCoroutinesApi
class MessageHandler(val player: Player) : AbstractMessageHandler() {

    override fun sendSmallVarp(id: Int, value: Int) {
        player.outgoingPackets.offer(VarpSmallMessage(id, value))
    }

    override fun sendLargeVarp(id: Int, value: Int) {
        player.outgoingPackets.offer(VarpLargeMessage(id, value))
    }

    override fun sendSkillUpdate(id: Int, level: Int, experience: Int) {
        player.outgoingPackets.offer(
            UpdateSkillMessage(
                id,
                level,
                experience
            )
        )
    }

    override fun sendRebuildRegion(login: Boolean, index: Int, x: Int, z: Int, height: Int, tileHash: Int) {
        player.outgoingPackets.offer(
            RebuildRegionMessage(
                login,
                index,
                x,
                z,
                height,
                tileHash
            )
        )
    }

    override fun sendClientScript(scriptId: Int, vararg params: Any) {
        player.outgoingPackets.offer(
            RunClientScriptMessage(
                scriptId,
                *params
            )
        )
    }

    override fun setWidgetText(widgetId: Int, defChildId: Int, text: String) {
        player.outgoingPackets.offer(
            IfSetTextMessage(
                (widgetId shl 16) or defChildId,
                text
            )
        )
    }

    override fun sendCloseSub(parentId: Int, childId: Int) {
        player.outgoingPackets.offer(
            IfCloseSubMessage(
                parentId,
                childId
            )
        )
    }

    override fun sendMoveSub(toParentId: Int, toChildId: Int, fromParentId: Int, fromChildId: Int) {
        player.outgoingPackets.offer(
            IfMoveSubMessage(
                toParentId,
                toChildId,
                fromParentId,
                fromChildId
            )
        )
    }

    override fun sendOpenOverlay(id: Int) {
        player.outgoingPackets.offer(IfOpenOverlayMessage(id))
    }

    override fun sendOpenSub(parentId: Int, childId: Int, component: Int, interType: Int) {
        player.outgoingPackets.offer(
            IfOpenSubMessage(
                parentId,
                childId,
                component,
                interType
            )
        )
    }

    override fun sendDisplayWidgetUpdate() {
        player.outgoingPackets.offer(UpdateDisplayWidgetsMessage())
    }

    override fun sendChatMessage(message: String, messageType: ChatMessageType) {
        player.outgoingPackets.offer(
            GameMessageMessage(
                messageType.type,
                player.displayName(),
                message
            )
        )
    }

    override fun sendAccessMask(widgetId: Int, defChildId: Int, minCs2ChildId: Int, maxCs2ChildId: Int, mask: Int) {
        player.outgoingPackets.offer(
            AccessMaskMessage(
                widgetId,
                defChildId,
                minCs2ChildId,
                maxCs2ChildId,
                mask
            )
        )
    }

    override fun sendItemContainerFull(
        interfaceId: Int,
        component: Int,
        containerKey: Int,
        container: ItemContainer<*>
    ) {
        player.outgoingPackets.offer(
            UpdateInventoryFullMessage(
                interfaceId,
                component,
                containerKey,
                container
            )
        )
    }

    override fun sendItemContainerPartial(
        interfaceId: Int,
        component: Int,
        containerKey: Int,
        container: ItemContainer<*>
    ) {
        player.outgoingPackets.offer(
            UpdateInventoryPartialMessage(
                container,
                interfaceId shl 16 and component,
                containerKey
            )
        )
    }
}