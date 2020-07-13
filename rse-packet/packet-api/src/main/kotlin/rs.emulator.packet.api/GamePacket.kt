package rs.emulator.packet.api

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 *
 * @author Chk
 */
open class GamePacket(
    override val opcode: Int,
    override val action: ActionType = ActionType.NONE,
    override val type: PacketType = PacketType.FIXED,
    override val payload: ByteBuf = Unpooled.buffer(1)
) : IPacketMessage
{

    val length: Int = payload.readableBytes()

    fun release() = payload.release()

}