package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MousePositionUpdateDecoder : PacketDecoder<MousePositionUpdateMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): MousePositionUpdateMessage
    {

        return MousePositionUpdateMessage()

    }

}