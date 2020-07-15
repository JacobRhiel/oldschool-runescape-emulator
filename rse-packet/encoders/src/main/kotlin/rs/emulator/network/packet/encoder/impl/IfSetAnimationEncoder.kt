package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetAnimationMessage

/**
 *
 * @author javatar
 */

class IfSetAnimationEncoder : PacketEncoder<IfSetAnimationMessage>() {
    override fun encode(message: IfSetAnimationMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, DataOrder.MIDDLE, message.componentHash)
        builder.put(DataType.SHORT, message.animationID)
    }
}