package rs.emulator.network.packet

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 *
 * @author Chk
 */
class GamePacket(val opcode: Int,
                 val action: ActionType = ActionType.NONE,
                 val type: PacketType = PacketType.FIXED,
                 val payload: ByteBuf = Unpooled.buffer(1)
)
{

    val length: Int = payload.readableBytes()

    fun release() = payload.release()

}