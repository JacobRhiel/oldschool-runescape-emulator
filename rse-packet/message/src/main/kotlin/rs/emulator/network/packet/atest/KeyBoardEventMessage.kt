package rs.emulator.network.packet.atest

import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class KeyBoardEventMessage(val events: List<KeyEvent>) : GamePacketMessage(3, type = PacketType.VARIABLE_SHORT)
{

    data class KeyEvent(val key: Char, val lastKeyPress: Long)

}