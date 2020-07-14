package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.PlayMIDISongMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class PlayMIDISongEncoder : PacketEncoder<PlayMIDISongMessage>() {
    override fun encode(message: PlayMIDISongMessage, builder: GamePacketBuilder) {
        builder.put(DataType.SHORT, DataOrder.LITTLE, message.songId)
    }
}