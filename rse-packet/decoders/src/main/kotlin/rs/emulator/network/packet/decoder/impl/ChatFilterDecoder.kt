package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ChatFilterMessage

/**
 *
 * @author javatar
 */

class ChatFilterDecoder : PacketDecoder<ChatFilterMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ChatFilterMessage {
        val publicChatFilter = reader.getUnsigned(DataType.BYTE).toInt()
        val privateChatFilter = reader.getUnsigned(DataType.BYTE).toInt()
        val tradeChatFilter = reader.getUnsigned(DataType.BYTE).toInt()
        return ChatFilterMessage(
            publicChatFilter,
            privateChatFilter,
            tradeChatFilter
        )
    }
}