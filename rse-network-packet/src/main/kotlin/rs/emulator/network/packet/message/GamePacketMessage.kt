package rs.emulator.network.packet.message

import com.google.common.base.MoreObjects
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.packet.ActionType
import rs.emulator.network.packet.PacketType

/**
 * Represents a single packet used in the in-game protocol.
 *
 * @author Graham
 * @author Chk
 */
open class GamePacketMessage(val opcode: Int,
                             val action: ActionType = ActionType.NONE,
                             val type: PacketType = PacketType.FIXED,
                             val payload: ByteBuf = Unpooled.buffer(1)
) : NetworkMessage
{

    val length: Int = payload.readableBytes()

    fun release() = payload.release()

    override fun toString(): String = MoreObjects.toStringHelper(this).add("opcode", opcode).add("type", type).add("length", length).toString()

}