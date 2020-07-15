package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.SpellOnObjMessage

/**
 *
 * @author javatar
 */

class SpellOnObjDecoder : PacketDecoder<SpellOnObjMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): SpellOnObjMessage {
        val slot = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        val spellComponentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE
        ).toInt()
        val spellChildIndex = reader.getUnsigned(
            DataType.SHORT
        ).toInt()
        val itemId = reader.getUnsigned(
            DataType.SHORT,
            DataOrder.LITTLE
        ).toInt()
        val componentHash = reader.getUnsigned(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE
        ).toInt()
        return SpellOnObjMessage(
            itemId,
            slot,
            componentHash,
            spellComponentHash,
            spellChildIndex
        )
    }
}