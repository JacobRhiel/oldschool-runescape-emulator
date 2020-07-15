package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UpdatePlayerWeightMessage

/**
 *
 * @author javatar
 */

class UpdatePlayerWeightEncoder : PacketEncoder<UpdatePlayerWeightMessage>() {
    override fun encode(message: UpdatePlayerWeightMessage, builder: GamePacketBuilder) {
        builder.put(DataType.SHORT, message.weight)
    }
}