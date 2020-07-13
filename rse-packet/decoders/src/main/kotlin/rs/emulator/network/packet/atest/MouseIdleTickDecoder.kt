package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MouseIdleTickDecoder : PacketDecoder<MouseIdleTickMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): MouseIdleTickMessage {

        return MouseIdleTickMessage()

    }

}