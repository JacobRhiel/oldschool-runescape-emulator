package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetScrollPositionMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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