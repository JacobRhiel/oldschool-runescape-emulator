package rs.emulator.entity.player

import rs.emulator.entity.actor.player.messages.AbstractMessageHandler
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.network.packet.message.outgoing.*

/**
 *
 * @author javatar
 */

class MessageHandler(val player: Player) : AbstractMessageHandler() {

    override fun sendSmallVarp(id: Int, value: Int) {
        player.outgoingPackets.offer(VarpSmallMessage(id, value))
    }

    override fun sendLargeVarp(id: Int, value: Int) {
        player.outgoingPackets.offer(VarpLargeMessage(id, value))
    }

    override fun sendClientScript(scriptId: Int, vararg params: Any) {
        player.outgoingPackets.offer(
            RunClientScriptMessage(
                scriptId,
                *params
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

    override fun sendChatMessage(message: String, messageType: Int) {
        player.outgoingPackets.offer(
            GameMessageMessage(
                messageType,
                player.displayName(),
                message
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