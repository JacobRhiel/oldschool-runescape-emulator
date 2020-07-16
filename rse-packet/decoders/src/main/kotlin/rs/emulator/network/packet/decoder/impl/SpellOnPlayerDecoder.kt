package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.SpellOnPlayerMessage

/**
 *
 * @author javatar
 */

class SpellOnPlayerDecoder : PacketDecoder<SpellOnPlayerMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): SpellOnPlayerMessage {
        val spellChildIndex = reader.getUnsigned(
            DataType.SHORT,
            DataTransformation.ADD
        ).toInt()
        val spellComponentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.MIDDLE
        ).toInt()
        val playerIndex = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE,
            DataTransformation.ADD
        ).toInt()
        val controlPressed = reader.getUnsigned(
            DataType.BYTE,
            DataTransformation.ADD
        ).toInt() == 1
        return SpellOnPlayerMessage(
            playerIndex,
            spellChildIndex,
            spellComponentHash,
            controlPressed
        )
    }
}