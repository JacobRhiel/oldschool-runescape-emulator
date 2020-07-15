package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetModelMessage

/**
 *
 * @author javatar
 */

class IfSetModelEncoder : PacketEncoder<IfSetModelMessage>() {
    override fun encode(message: IfSetModelMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, DataOrder.MIDDLE, message.componentHash)
        builder.put(DataType.SHORT, message.modelID)
    }
}