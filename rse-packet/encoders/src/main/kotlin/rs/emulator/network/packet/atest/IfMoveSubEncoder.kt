package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class IfMoveSubEncoder : PacketEncoder<IfMoveSubMessage>()
{

    override fun encode(message: IfMoveSubMessage, player: Player, builder: GamePacketBuilder)
    {

        val toHash = (message.toParent shl 16) or message.toChild

        val fromHash = (message.fromParent shl 16) or message.fromChild

        builder.put(DataType.INT, DataOrder.MIDDLE, toHash)

        builder.put(DataType.INT, fromHash)

    }

}