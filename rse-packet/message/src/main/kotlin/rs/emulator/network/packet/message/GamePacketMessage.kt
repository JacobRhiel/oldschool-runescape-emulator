package rs.emulator.network.packet.message

import com.google.common.base.MoreObjects
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import rs.emulator.network.message.NetworkMessage
import rs.emulator.packet.api.PacketType
import rs.emulator.packet.api.*

/**
 * Represents a single packet used in the in-game protocol.
 *
 * @author Graham
 * @author Chk
 */
open class GamePacketMessage(
    override val opcode: Int,
    override val action: ActionType = ActionType.NONE,
    override val type: PacketType = PacketType.FIXED,
    val length: Int = 0,
    val ignore: Boolean = false,
    override val payload: ByteBuf = Unpooled.buffer(if(length == 0) 1 else length)
) : NetworkMessage, IPacketMessage
{

    val payloadSize: Int = payload.readableBytes()

    fun release() = payload.release()

    override fun toString(): String = MoreObjects.toStringHelper(this).add("opcode", opcode).add("type", type).add("length", payloadSize).toString()

}