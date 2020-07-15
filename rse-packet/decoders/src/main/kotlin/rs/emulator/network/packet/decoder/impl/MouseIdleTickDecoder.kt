package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.MouseIdleTickMessage

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