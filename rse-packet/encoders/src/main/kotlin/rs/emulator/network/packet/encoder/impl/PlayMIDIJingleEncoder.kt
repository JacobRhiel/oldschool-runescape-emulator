package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.PlayMIDIJingleMessage

/**
 *
 * @author javatar
 */

class PlayMIDIJingleEncoder : PacketEncoder<PlayMIDIJingleMessage>() {
    override fun encode(message: PlayMIDIJingleMessage, builder: GamePacketBuilder) {
        builder.put(DataType.SHORT, DataOrder.LITTLE, message.jingleId)
        builder.put(DataType.TRI_BYTE, message.unknown)
    }
}