package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
data class PublicChatMessage(val chatType: Int,
                        val color: Int,
                        val effect: Int,
                        val messageLength: Int,
                        val data: ByteArray
) : GamePacketMessage(12, type = PacketType.VARIABLE_BYTE)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicChatMessage

        if (chatType != other.chatType) return false
        if (color != other.color) return false
        if (effect != other.effect) return false
        if (messageLength != other.messageLength) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chatType
        result = 31 * result + color
        result = 31 * result + effect
        result = 31 * result + messageLength
        result = 31 * result + data.contentHashCode()
        return result
    }
}