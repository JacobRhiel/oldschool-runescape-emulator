package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.PrivateMessageMessage
import rs.emulator.network.packet.encoder.PacketEncoder

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