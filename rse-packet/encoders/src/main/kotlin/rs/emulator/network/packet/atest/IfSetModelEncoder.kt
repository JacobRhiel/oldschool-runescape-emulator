package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetModelMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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