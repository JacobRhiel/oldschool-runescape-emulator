package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetWidgetItemsMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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