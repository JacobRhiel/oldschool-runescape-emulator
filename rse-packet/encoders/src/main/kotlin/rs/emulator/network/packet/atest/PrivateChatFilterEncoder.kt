package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.PrivateChatFilterMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class PrivateChatFilterEncoder : PacketEncoder<PrivateChatFilterMessage>() {
    override fun encode(message: PrivateChatFilterMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.setting)
    }
}