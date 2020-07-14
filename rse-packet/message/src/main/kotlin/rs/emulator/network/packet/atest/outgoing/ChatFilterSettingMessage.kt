package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ChatFilterSettingMessage(val publicChatMode: Int, val tradeChatMode: Int) : GamePacketMessage(68)