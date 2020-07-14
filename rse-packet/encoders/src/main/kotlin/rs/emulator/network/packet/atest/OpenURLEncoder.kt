package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.OpenURLMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class OpenURLEncoder : PacketEncoder<OpenURLMessage>() {
    override fun encode(message: OpenURLMessage, builder: GamePacketBuilder) {
        builder.putString(message.url)
    }
}