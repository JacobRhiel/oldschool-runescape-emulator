package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ObjOnPlayerMessage

/**
 *
 * @author javatar
 */

class ObjOnPlayerDecoder : PacketDecoder<ObjOnPlayerMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ObjOnPlayerMessage {
        val controlPressed = reader.getUnsigned(DataType.BYTE).toInt() == 1
        val targetPlayerIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE
        ).toInt()
        val selectedItemId = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val componentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.LITTLE
        ).toInt()
        val selectedItemSlot = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        return ObjOnPlayerMessage(
            targetPlayerIndex,
            selectedItemId,
            componentHash,
            selectedItemSlot,
            controlPressed
        )
    }
}