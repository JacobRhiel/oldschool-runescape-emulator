package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ConsoleCommandMessage

/**
 *
 * @author Chk
 */
class ConsoleCommandDecoder : PacketDecoder<ConsoleCommandMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ConsoleCommandMessage {

        return ConsoleCommandMessage(reader.string)

    }

}