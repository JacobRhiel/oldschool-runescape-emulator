package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ObjOnNpcMessage

/**
 *
 * @author javatar
 */

class ObjOnNpcDecoder : PacketDecoder<ObjOnNpcMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ObjOnNpcMessage {
        val controlPressed = reader.getUnsigned(
            DataType.BYTE,
            DataTransformation.SUBTRACT
        ).toInt() == 1
        val selectedComponentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE
        ).toInt()
        val npcIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE
        ).toInt()
        val selectedItemSlot = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val selectedItemId = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        return ObjOnNpcMessage(
            npcIndex,
            selectedItemId,
            selectedItemSlot,
            selectedComponentHash,
            controlPressed
        )
    }
}