package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetTextMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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