package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.SpellOnGroundObjMessage

/**
 *
 * @author javatar
 */

class SpellOnGroundObjDecoder : PacketDecoder<SpellOnGroundObjMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): SpellOnGroundObjMessage {
        val x = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val spellChildIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE
        ).toInt()
        val controlPressed = reader.getUnsigned(
            DataType.BYTE
        ).toInt() == 1
        val itemId = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE,
            DataTransformation.ADD
        ).toInt()
        val spellComponentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE
        ).toInt()
        val y = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        return SpellOnGroundObjMessage(
            itemId,
            x,
            y,
            spellChildIndex,
            spellComponentHash,
            controlPressed
        )
    }
}