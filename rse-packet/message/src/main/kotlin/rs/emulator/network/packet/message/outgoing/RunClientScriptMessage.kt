package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class RunClientScriptMessage(val id: Int,
                             vararg val args: Any
) : GamePacketMessage(49, type = PacketType.VARIABLE_SHORT)
