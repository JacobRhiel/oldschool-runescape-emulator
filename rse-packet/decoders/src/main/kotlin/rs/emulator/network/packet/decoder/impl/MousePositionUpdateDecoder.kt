package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.MousePositionUpdateMessage

/**
 *
 * @author Chk
 */
class MousePositionUpdateDecoder : PacketDecoder<MousePositionUpdateMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): MousePositionUpdateMessage {


        return MousePositionUpdateMessage()

    }

}