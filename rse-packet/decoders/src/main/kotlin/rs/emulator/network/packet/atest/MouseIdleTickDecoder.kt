package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MouseIdleTickDecoder : PacketDecoder<MouseIdleTickMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): MouseIdleTickMessage
    {

        return MouseIdleTickMessage()

    }

}