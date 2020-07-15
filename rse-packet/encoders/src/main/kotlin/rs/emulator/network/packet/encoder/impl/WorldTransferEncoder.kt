package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.WorldTransferMessage

/**
 *
 * @author javatar
 */

class WorldTransferEncoder : PacketEncoder<WorldTransferMessage>() {
    override fun encode(message: WorldTransferMessage, builder: GamePacketBuilder) {
        builder.putString(message.domain)
        builder.put(DataType.SHORT, message.worldNumber)
        builder.put(DataType.INT, message.members)
    }
}