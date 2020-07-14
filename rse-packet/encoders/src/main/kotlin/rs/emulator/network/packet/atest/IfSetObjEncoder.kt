package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetObjMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class IfSetObjEncoder : PacketEncoder<IfSetObjMessage>() {
    override fun encode(message: IfSetObjMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, message.componentHash)
        builder.put(DataType.SHORT, DataTransformation.ADD, message.itemId)
        builder.put(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE,
            message.amount
        )
    }
}