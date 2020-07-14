package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.GrandExchangeOfferMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class GrandExchangeOfferEncoder : PacketEncoder<GrandExchangeOfferMessage>() {
    override fun encode(message: GrandExchangeOfferMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.slot)
        builder.put(DataType.BYTE, message.state)
        if (message.state != 0) {
            builder.put(DataType.SHORT, message.itemId)
            builder.put(DataType.INT, message.price)
            builder.put(DataType.INT, message.totalQuantity)
            builder.put(DataType.INT, message.quantitySold)
            builder.put(DataType.INT, message.totalSpent)
        }
    }
}