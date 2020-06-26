package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class WindowStatusDecoder : PacketDecoder<WindowStatusMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader) : WindowStatusMessage
    {
        return WindowStatusMessage(
            reader.getSigned(DataType.BYTE).toInt(),
            reader.getSigned(DataType.SHORT).toInt(),
            reader.getSigned(DataType.SHORT).toInt()
        )
    }

}