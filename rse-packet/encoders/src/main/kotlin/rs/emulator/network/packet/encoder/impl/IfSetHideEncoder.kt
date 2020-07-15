package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfSetHideMessage

/**
 *
 * @author javatar
 */

class IfSetHideEncoder : PacketEncoder<IfSetHideMessage>() {
    override fun encode(message: IfSetHideMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, message.componentHash)
        builder.put(DataType.BYTE, if (message.hidden) 1 else 0)
    }
}