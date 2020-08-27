package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.IfButtonMessage

/**
 *
 * @author Chk
 */
class IfButtonDecoder : PacketDecoder<IfButtonMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): IfButtonMessage {

        val hash = reader.getSigned(DataType.INT).toInt()

        val slot = reader.getSigned(DataType.SHORT).toInt()

        val item = reader.getSigned(DataType.SHORT).toInt()

        return IfButtonMessage(
                opcode,
                hash = hash,
                option = opcode,
                slot = if (slot == 0xFFFF) -1 else slot,
                item = if (item == 0xFFFF) -1 else item
        )

    }

}