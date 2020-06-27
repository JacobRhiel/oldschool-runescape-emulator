package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class IfButtonDecoder : PacketDecoder<IfButtonMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): IfButtonMessage
    {

        val hash = reader.getSigned(DataType.INT).toInt()

        val slot = reader.getSigned(DataType.SHORT).toInt()

        val item = reader.getSigned(DataType.SHORT).toInt()

        return IfButtonMessage(
            hash = hash,
            option = opcode,
            slot = if (slot == 0xFFFF) -1 else slot,
            item = if (item == 0xFFFF) -1 else item
        )

    }

}