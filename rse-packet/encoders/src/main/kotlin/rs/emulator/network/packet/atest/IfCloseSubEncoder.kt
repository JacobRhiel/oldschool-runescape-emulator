package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class IfCloseSubEncoder : PacketEncoder<IfCloseSubMessage>()
{

    override fun encode(message: IfCloseSubMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.INT, (message.parent shl 16) or message.child)

    }

}