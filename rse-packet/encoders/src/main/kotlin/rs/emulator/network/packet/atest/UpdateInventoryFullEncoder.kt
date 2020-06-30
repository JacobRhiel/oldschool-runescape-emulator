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

        builder.put(DataType.SHORT, message.items.size)

        val buf = GamePacketBuilder()

        message.items.forEach { item ->

            buf.put(DataType.BYTE, 255.coerceAtMost(1/*item.amount*/))

            buf.put(DataType.SHORT, DataOrder.LITTLE, 4152/*item.id + 1*/)

            /*if (item.amount >= 255) {
                buf.put(DataType.INT, DataOrder.LITTLE, item.amount)
            }*/
        }

        val data = ByteArray(buf.byteBuf.readableBytes())

        buf.byteBuf.readBytes(data)

        builder.putBytes(data)

    }

}