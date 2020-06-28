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
class VarpLargeEncoder : PacketEncoder<VarpLargeMessage>()
{

    override fun encode(message: VarpLargeMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.id)

        builder.put(DataType.INT, DataOrder.MIDDLE, message.value)

    }

}