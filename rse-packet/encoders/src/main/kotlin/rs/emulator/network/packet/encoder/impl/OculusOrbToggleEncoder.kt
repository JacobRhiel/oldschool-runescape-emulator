package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.OculusOrbToggleMessage

/**
 *
 * @author javatar
 */

class OculusOrbToggleEncoder : PacketEncoder<OculusOrbToggleMessage>() {
    override fun encode(message: OculusOrbToggleMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, if (message.toggle) 1 else 0)
    }
}