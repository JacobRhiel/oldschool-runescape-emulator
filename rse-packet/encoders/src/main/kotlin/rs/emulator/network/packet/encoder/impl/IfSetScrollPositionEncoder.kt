package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetScrollPositionMessage

/**
 *
 * @author javatar
 */

class IfSetScrollPositionEncoder : PacketEncoder<IfSetScrollPositionMessage>() {
    override fun encode(message: IfSetScrollPositionMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, DataOrder.MIDDLE, message.componentHash)
        builder.put(DataType.SHORT, message.scrollY)
    }
}