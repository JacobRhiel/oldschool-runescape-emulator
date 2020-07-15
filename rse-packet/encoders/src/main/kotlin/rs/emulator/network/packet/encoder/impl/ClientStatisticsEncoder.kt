package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.ClientStatisticsMessage

/**
 *
 * @author javatar
 */

class ClientStatisticsEncoder : PacketEncoder<ClientStatisticsMessage>() {
    override fun encode(message: ClientStatisticsMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, message.unknown1)
        builder.put(DataType.INT, message.unknown2)
    }
}