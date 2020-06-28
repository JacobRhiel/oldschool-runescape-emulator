package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MouseClickDecoder : PacketDecoder<MouseClickMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): MouseClickMessage
    {

        val hash = reader.getSigned(DataType.SHORT).toInt()

        val timeStamp = (hash shr 1).toLong() //todo: + last button == 2 ? 1 : 0

        val lastPressX = reader.getSigned(DataType.SHORT).toInt()

        val lastPressY = reader.getSigned(DataType.SHORT).toInt()

        return MouseClickMessage(timeStamp, lastPressX, lastPressY, 0)

    }

}