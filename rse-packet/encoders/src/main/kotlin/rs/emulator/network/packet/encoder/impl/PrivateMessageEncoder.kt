package rs.emulator.network.packet.encoder.impl

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.PrivateMessageMessage

/**
 *
 * @author javatar
 */

class PrivateMessageEncoder : PacketEncoder<PrivateMessageMessage>() {
    override fun encode(message: PrivateMessageMessage, builder: GamePacketBuilder) {
        builder.putString(message.message)
        //TODO - finish this
    }
}