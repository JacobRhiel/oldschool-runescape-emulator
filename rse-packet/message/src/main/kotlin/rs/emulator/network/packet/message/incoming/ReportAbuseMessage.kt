package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class ReportAbuseMessage(val name: String, val abuseType: Int, val isStaff: Boolean) :
    GamePacketMessage(100, type = PacketType.VARIABLE_BYTE)