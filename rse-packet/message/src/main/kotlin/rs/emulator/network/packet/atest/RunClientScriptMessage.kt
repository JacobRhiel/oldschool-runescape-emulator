package rs.emulator.network.packet.atest

import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class RunClientScriptMessage(val id: Int,
                             vararg val args: Any
) : GamePacketMessage(49, type = PacketType.VARIABLE_SHORT)
