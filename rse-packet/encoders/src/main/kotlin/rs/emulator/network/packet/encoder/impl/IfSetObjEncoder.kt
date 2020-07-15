package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetObjMessage

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