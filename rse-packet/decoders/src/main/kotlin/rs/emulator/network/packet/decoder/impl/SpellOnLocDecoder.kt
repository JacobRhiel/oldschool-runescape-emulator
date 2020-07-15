package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.SpellOnLocMessage

/**
 *
 * @author javatar
 */

class SpellOnLocDecoder : PacketDecoder<SpellOnLocMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): SpellOnLocMessage {
        val x = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        val componentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.MIDDLE
        ).toInt()
        val controlPressed = reader.getUnsigned(
            DataType.BYTE
        ).toInt() == 1
        val locId = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val y = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        val specllChildIndex = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        return SpellOnLocMessage(
            specllChildIndex,
            componentHash,
            x,
            y,
            locId,
            controlPressed
        )
    }
}