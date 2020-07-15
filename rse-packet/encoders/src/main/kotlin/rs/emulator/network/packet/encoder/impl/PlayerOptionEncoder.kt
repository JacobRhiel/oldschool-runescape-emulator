package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.message.outgoing.PlayerOptionMessage
import rs.emulator.packet.api.IPacketEncoder

/**
 *
 * @author javatar
 */

class PlayerOptionEncoder : IPacketEncoder<PlayerOptionMessage> {
    override fun encode(message: PlayerOptionMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.priority)
        builder.putString(message.option)
        builder.put(DataType.BYTE, message.position)
    }
}