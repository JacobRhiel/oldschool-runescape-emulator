package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author javatar
 */

class ZeroSizeDecoder(val supplier: () -> IPacketMessage) : PacketDecoder<IPacketMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): IPacketMessage {
        return supplier()
    }
}