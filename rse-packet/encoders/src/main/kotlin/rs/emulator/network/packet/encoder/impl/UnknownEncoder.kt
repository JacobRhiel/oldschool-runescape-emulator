package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UnknownMessage

/**
 *
 * @author javatar
 */

class UnknownEncoder : PacketEncoder<UnknownMessage>() {
    override fun encode(message: UnknownMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, if (message.value) 1 else 0)
    }
}