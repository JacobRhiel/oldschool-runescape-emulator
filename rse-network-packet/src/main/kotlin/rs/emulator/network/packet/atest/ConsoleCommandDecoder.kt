package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class ConsoleCommandDecoder : PacketDecoder<ConsoleCommandMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ConsoleCommandMessage
    {

        return ConsoleCommandMessage(reader.string)

    }

}