package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.DisconnectedMessage

/**
 *
 * @author javatar
 */

class DisconnectedEncoder : PacketEncoder<DisconnectedMessage>() {
    override fun encode(message: DisconnectedMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.disconnectType)
    }
}