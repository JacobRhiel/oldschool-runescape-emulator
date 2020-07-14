package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class IfSetTextMessage(val componentHash: Int, val text: String) :
    GamePacketMessage(55, type = PacketType.VARIABLE_SHORT)