package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ClickWorldMapMessage

/**
 *
 * @author Chk
 */
class ClickWorldMapEventDecoder : PacketDecoder<ClickWorldMapMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ClickWorldMapMessage
    {

        val coordinateHash = reader.getUnsigned(DataType.INT, DataOrder.INVERSED_MIDDLE).toInt()

        return ClickWorldMapMessage(coordinateHash = coordinateHash)

    }

}