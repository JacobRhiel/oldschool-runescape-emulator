package rs.emulator.network.packet

import com.google.common.base.MoreObjects
import io.netty.buffer.ByteBuf

/**
 * Represents a single packet used in the in-game protocol.
 *
 * @author Graham
 * @author Chk
 */
class GamePacket(val opcode: Int,
                 val action: ActionType = ActionType.NONE,
                 val type: PacketType,
                 val payload: ByteBuf
)
{

    val length: Int = payload.readableBytes()

    fun release() = payload.release()

    override fun toString(): String = MoreObjects.toStringHelper(this).add("opcode", opcode).add("type", type).add("length", length).toString()

}