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
class UpdateInventoryFullEncoder : PacketEncoder<UpdateInventoryFullMessage>()
{

    override fun encode(message: UpdateInventoryFullMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.INT, message.componentHash)

        builder.put(DataType.SHORT, message.containerKey)

        builder.put(DataType.SHORT, message.container.array.size)

        message.container.forEach { item ->
            println(item)
            builder.put(DataType.BYTE, 255.coerceAtMost(item.amount))
            builder.put(DataType.SHORT, DataOrder.LITTLE, item.id + 1)
            if (item.amount >= 255) {
                builder.put(DataType.INT, DataOrder.LITTLE, item.amount)
            }
        }
    }

}