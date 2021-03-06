package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class IfOpenOverlayEncoder : PacketEncoder<IfOpenOverlayMessage>()
{

    override fun encode(message: IfOpenOverlayMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.id)

    }

}