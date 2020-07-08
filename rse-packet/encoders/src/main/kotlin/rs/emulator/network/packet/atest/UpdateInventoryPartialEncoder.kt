package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class UpdateInventoryPartialEncoder : PacketEncoder<UpdateInventoryPartialMessage>() {
    override fun encode(message: UpdateInventoryPartialMessage, player: Player, builder: GamePacketBuilder) {
        builder.put(DataType.INT, message.componentHash)
        builder.put(DataType.SHORT, message.containerKey)
        message.container.array.forEachIndexed { index, item ->
            if (item !== ItemData.EMPTY) {
                builder.put(DataType.SHORT, item.id + 1)
                builder.put(DataType.BYTE, 255.coerceAtMost(item.amount))

                if (item.amount >= 255)
                    builder.put(DataType.INT, item.amount)
                builder.put(DataType.SHORT, index)
            }
        }
    }

}