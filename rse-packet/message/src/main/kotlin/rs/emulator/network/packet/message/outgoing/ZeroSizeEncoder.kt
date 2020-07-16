package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.IPacketEncoder
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author javatar
 */

class ZeroSizeEncoder<T : IPacketMessage> : IPacketEncoder<T> {
    override fun encode(message: T, builder: GamePacketBuilder) {}
}