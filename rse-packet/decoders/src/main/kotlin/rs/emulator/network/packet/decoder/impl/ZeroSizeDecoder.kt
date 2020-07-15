package rs.emulator.network.packet.decoder.impl

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.packet.api.ActionType
import rs.emulator.packet.api.IPacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

class ZeroSizeDecoder : PacketDecoder<IPacketMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): IPacketMessage {
        return object : IPacketMessage {
            override val opcode: Int
                get() = opcode
            override val action: ActionType
                get() = ActionType.NONE
            override val type: PacketType
                get() = PacketType.FIXED
            override val payload: ByteBuf
                get() = ByteBufAllocator.DEFAULT.buffer()
        }
    }
}