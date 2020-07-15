package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.ToggleMinimapMessage

/**
 *
 * @author javatar
 */

class ToggleMinimapEncoder : PacketEncoder<ToggleMinimapMessage>() {
    override fun encode(message: ToggleMinimapMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, if (message.toggle) 1 else 0)
    }
}