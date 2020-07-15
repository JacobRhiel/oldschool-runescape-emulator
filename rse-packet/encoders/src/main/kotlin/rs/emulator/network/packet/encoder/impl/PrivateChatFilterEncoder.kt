package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.PrivateChatFilterMessage

/**
 *
 * @author javatar
 */

class PrivateChatFilterEncoder : PacketEncoder<PrivateChatFilterMessage>() {
    override fun encode(message: PrivateChatFilterMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.setting)
    }
}