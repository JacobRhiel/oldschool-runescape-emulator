package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.DisconnectedMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class DisconnectedEncoder : PacketEncoder<DisconnectedMessage>() {
    override fun encode(message: DisconnectedMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.disconnectType)
    }
}