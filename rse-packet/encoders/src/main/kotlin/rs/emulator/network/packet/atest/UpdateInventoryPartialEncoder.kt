package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class UpdateInventoryPartialEncoder : PacketEncoder<UpdateInventoryPartialMessage>()
{

    override fun encode(message: UpdateInventoryPartialMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.INT, message.componentHash)

        builder.put(DataType.SHORT, message.containerKey)

        val size = message.oldItems.size

        val buf = GamePacketBuilder()

        for (index in 0 until size)
        {

            val oldItem = message.oldItems[index]

            val newItem = message.newItems[index]

            //val idMatch = oldItem?.id == newItem?.id

            //val amountMatch = oldItem?.value == newItem?.amount

            //if (!idMatch || !amountMatch) {

            buf.putSmart(index)

            if (newItem != null)
            {

                buf.put(DataType.SHORT, 4152 /*newItem.id + 1*/)

                buf.put(DataType.BYTE, Math.min(255, 1/*newItem.amount*/))

                //if (newItem.amount >= 255)
                //    buf.put(DataType.INT, 1/*newItem.amount*/)
                //else
                buf.put(DataType.SHORT, 0)

            }
        }

        val data = ByteArray(buf.byteBuf.readableBytes())

        buf.byteBuf.readBytes(data)

        builder.putBytes(data)

    }

}