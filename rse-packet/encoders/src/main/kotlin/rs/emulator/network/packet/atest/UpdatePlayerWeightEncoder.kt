package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.UpdatePlayerWeightMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class UpdatePlayerWeightEncoder : PacketEncoder<UpdatePlayerWeightMessage>() {
    override fun encode(message: UpdatePlayerWeightMessage, builder: GamePacketBuilder) {
        builder.put(DataType.SHORT, message.weight)
    }
}