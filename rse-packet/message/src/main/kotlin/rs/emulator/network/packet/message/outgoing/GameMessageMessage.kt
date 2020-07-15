package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class GameMessageMessage(val messageType: Int,
                         val username: String? = "",
                         val message: String) : GamePacketMessage(81, type = PacketType.VARIABLE_BYTE)
{
}