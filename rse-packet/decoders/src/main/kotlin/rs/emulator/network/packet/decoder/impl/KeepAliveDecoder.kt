package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.KeepAliveMessage

/**
 *
 * @author Chk
 */
class KeepAliveDecoder : PacketDecoder<KeepAliveMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): KeepAliveMessage {
        return KeepAliveMessage()
    }

}