package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetTextMessage

/**
 *
 * @author javatar
 */

class IfSetTextEncoder : PacketEncoder<IfSetTextMessage>() {
    override fun encode(message: IfSetTextMessage, builder: GamePacketBuilder) {
        builder.put(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE,
            message.componentHash
        )
        builder.putString(message.text)
    }
}