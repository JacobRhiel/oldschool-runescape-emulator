package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetPlayerChatHeadMessage

/**
 *
 * @author javatar
 */

class IfSetPlayerChatHeadEncoder : PacketEncoder<IfSetPlayerChatHeadMessage>() {
    override fun encode(message: IfSetPlayerChatHeadMessage, builder: GamePacketBuilder) {
        builder.put(
            DataType.INT,
            DataOrder.INVERSED_MIDDLE,
            message.componentHash
        )
    }
}