package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.OculusOrbToggleMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class OculusOrbToggleEncoder : PacketEncoder<OculusOrbToggleMessage>() {
    override fun encode(message: OculusOrbToggleMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, if (message.toggle) 1 else 0)
    }
}