package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class DragItemMessage(
    val draggedItemId: Int,
    val draggedHash: Int,
    val draggedChildIndex: Int,
    val clickedItemId: Int,
    val clickedHash: Int,
    val clickedChildIndex: Int
) : GamePacketMessage(58)