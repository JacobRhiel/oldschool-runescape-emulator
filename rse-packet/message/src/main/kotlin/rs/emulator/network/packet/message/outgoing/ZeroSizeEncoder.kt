package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.IPacketEncoder
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author javatar
 */

class ZeroSizeEncoder : IPacketEncoder<IPacketMessage> {
    override fun encode(message: IPacketMessage, builder: GamePacketBuilder) {}
}