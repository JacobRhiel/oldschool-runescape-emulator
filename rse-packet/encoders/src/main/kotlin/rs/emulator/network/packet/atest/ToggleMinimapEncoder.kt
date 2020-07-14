package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.ToggleMinimapMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class ToggleMinimapEncoder : PacketEncoder<ToggleMinimapMessage>() {
    override fun encode(message: ToggleMinimapMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, if (message.toggle) 1 else 0)
    }
}