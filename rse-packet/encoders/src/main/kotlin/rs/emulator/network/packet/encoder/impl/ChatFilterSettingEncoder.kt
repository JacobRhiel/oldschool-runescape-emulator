package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.ChatFilterSettingMessage

/**
 *
 * @author javatar
 */

class ChatFilterSettingEncoder : PacketEncoder<ChatFilterSettingMessage>() {
    override fun encode(message: ChatFilterSettingMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.publicChatMode)
        builder.put(DataType.BYTE, DataTransformation.ADD, message.tradeChatMode)
    }
}