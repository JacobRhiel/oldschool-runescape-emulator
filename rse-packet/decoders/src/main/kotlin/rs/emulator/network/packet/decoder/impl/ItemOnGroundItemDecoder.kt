package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ItemOnGroundItemMessage

/**
 *
 * @author javatar
 */

class ItemOnGroundItemDecoder : PacketDecoder<ItemOnGroundItemMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ItemOnGroundItemMessage {
        val selectedItemId = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE,
            DataTransformation.ADD
        )
        val item = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        )
        val x = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        )
        val crlRun = reader.getUnsigned(
            DataType.BYTE,
            DataTransformation.NEGATE
        ).toInt() == 1
        val y = reader.getUnsigned(DataType.SHORT)
        val selectedItemWidget = reader.getUnsigned(
            DataType.INT,
            DataOrder.LITTLE
        )
        val selectedItemSlot = reader.getUnsigned(DataType.SHORT)
        return ItemOnGroundItemMessage(
            item.toInt(),
            selectedItemSlot.toInt(),
            selectedItemId.toInt(),
            selectedItemWidget.toInt(),
            x.toInt(),
            y.toInt(),
            crlRun
        )
    }
}