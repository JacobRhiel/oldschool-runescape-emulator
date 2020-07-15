package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.HintArrowMessage

/**
 *
 * @author javatar
 */

class HintArrowEncoder : PacketEncoder<HintArrowMessage>() {
    override fun encode(message: HintArrowMessage, builder: GamePacketBuilder) {
        val type = message.hintArrayType
        builder.put(DataType.BYTE, type)
        if (type in 2..6 && message.entityIndex == -1) {
            builder.put(DataType.SHORT, message.x)
            builder.put(DataType.SHORT, message.z)
            builder.put(DataType.BYTE, message.y)
        } else if (type == 1 || type == 10) {
            builder.put(DataType.SHORT, message.entityIndex)
        }
    }
}