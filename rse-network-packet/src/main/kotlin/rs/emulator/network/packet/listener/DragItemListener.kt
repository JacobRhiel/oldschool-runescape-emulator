package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.DragItemMessage

/**
 *
 * @author javatar
 */

class DragItemListener : GamePacketListener<DragItemMessage> {
    override fun handle(channel: Channel, player: Player, message: DragItemMessage) {

        player.messages().sendChatMessage("Dragged Item ID : ${message.draggedItemId}")
        player.messages()
            .sendChatMessage("Dragged Widget ID : ${message.draggedHash shr 16} - index ${message.draggedChildIndex}")
        player.messages().sendChatMessage("Clicked Item ID : ${message.clickedItemId}")
        player.messages()
            .sendChatMessage("Clicked Widget ID : ${message.clickedHash shr 16} - index ${message.clickedChildIndex}")

    }
}