package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetNpcHeadMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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