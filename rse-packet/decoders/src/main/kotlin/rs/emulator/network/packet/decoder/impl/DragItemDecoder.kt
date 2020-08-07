package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.DragItemMessage

/**
 *
 * @author javatar
 */

class DragItemDecoder : PacketDecoder<DragItemMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): DragItemMessage {
        val draggedItemId = reader.getUnsigned(DataType.SHORT).toInt()
        val draggedChildIndex = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
        val clickedItemId = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
        val clickedChildIndex = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
        val clickedHash = reader.getUnsigned(DataType.INT, DataOrder.MIDDLE).toInt()
        val draggedHash = reader.getUnsigned(DataType.INT, DataOrder.INVERSED_MIDDLE).toInt()
        return DragItemMessage(
            draggedItemId,
            draggedHash,
            draggedChildIndex,
            clickedItemId,
            clickedHash,
            clickedChildIndex
        )
    }
}