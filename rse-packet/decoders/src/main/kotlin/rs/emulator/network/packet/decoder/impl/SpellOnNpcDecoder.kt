package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.SpellOnNpcMessage

/**
 *
 * @author javatar
 */

class SpellOnNpcDecoder : PacketDecoder<SpellOnNpcMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): SpellOnNpcMessage {
        val controlPressed = reader.getUnsigned(
            DataType.BYTE,
            DataTransformation.ADD
        ).toInt() == 1
        val spellComponentHash = reader.getUnsigned(
            DataType.INT
        ).toInt()
        val npcIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE,
            DataTransformation.ADD
        ).toInt()
        val spellChildIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE
        ).toInt()
        return SpellOnNpcMessage(
            npcIndex,
            spellChildIndex,
            spellComponentHash,
            controlPressed
        )
    }
}