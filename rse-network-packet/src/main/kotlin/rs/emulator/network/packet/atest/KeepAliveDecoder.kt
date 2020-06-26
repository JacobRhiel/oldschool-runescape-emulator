package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class KeepAliveDecoder : PacketDecoder<KeepAliveMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): KeepAliveMessage
    {
        return KeepAliveMessage()
    }

}