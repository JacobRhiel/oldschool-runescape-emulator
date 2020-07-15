package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetNpcHeadMessage

/**
 *
 * @author javatar
 */

class IfSetNpcHeadEncoder : PacketEncoder<IfSetNpcHeadMessage>() {
    override fun encode(message: IfSetNpcHeadMessage, builder: GamePacketBuilder) {
        builder.put(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE,
            message.componentHash
        )
        builder.put(
            DataType.SHORT,
            DataTransformation.ADD,
            message.npcId
        )
    }
}