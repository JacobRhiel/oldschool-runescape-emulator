package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ChatFilterMessage(val publicChatMode: Int, val privateChatMode: Int, val tradeChatMode: Int) :
    GamePacketMessage(83)