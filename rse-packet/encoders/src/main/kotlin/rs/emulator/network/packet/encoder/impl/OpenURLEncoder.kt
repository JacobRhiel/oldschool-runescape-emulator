package rs.emulator.network.packet.encoder.impl

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.OpenURLMessage

/**
 *
 * @author javatar
 */

class OpenURLEncoder : PacketEncoder<OpenURLMessage>() {
    override fun encode(message: OpenURLMessage, builder: GamePacketBuilder) {
        builder.putString(message.url)
    }
}