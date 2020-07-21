package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ObjOnObjMessage

/**
 *
 * @author javatar
 */

class ObjOnObjDecoder : PacketDecoder<ObjOnObjMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ObjOnObjMessage {
        val componentHash = reader.getUnsigned(
            DataType.INT
        ).toInt()
        val itemId = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val selectedComponentHash = reader.getUnsigned(
            DataType.INT
        ).toInt()
        val slot = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        val selectedSlot = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val selectedItemId = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        return ObjOnObjMessage(
            itemId,
            slot,
            selectedItemId,
            selectedSlot,
            componentHash,
            selectedComponentHash
        )
    }
}