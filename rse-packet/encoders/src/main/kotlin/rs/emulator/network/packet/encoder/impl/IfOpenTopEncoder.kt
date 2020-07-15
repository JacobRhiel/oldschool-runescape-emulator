package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfOpenTopMessage

/**
 *
 * @author javatar
 */

class IfOpenTopEncoder : PacketEncoder<IfOpenTopMessage>() {
    override fun encode(message: IfOpenTopMessage, builder: GamePacketBuilder) {
        builder.put(
            DataType.SHORT,
            DataOrder.LITTLE,
            DataTransformation.ADD,
            message.rootWidgetIndex
        )
    }
}