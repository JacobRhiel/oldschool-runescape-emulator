package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.IfSetHideMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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