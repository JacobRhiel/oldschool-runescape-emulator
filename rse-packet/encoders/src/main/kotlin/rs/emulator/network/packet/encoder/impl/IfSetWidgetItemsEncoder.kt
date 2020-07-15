package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetWidgetItemsMessage

/**
 * The 190 Client reads Inverted Int
 * @author javatar
 */

class IfSetWidgetItemsEncoder : PacketEncoder<IfSetWidgetItemsMessage>() {
    override fun encode(message: IfSetWidgetItemsMessage, builder: GamePacketBuilder) {
        builder.put(
            DataType.INT,
            message.componentHash
        )
    }
}