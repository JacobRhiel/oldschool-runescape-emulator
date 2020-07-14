package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.ClientStatisticsMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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