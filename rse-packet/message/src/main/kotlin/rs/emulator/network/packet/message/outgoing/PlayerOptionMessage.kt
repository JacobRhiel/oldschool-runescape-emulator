package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class PlayerOptionMessage(val option: String, val position: Int, val priority: Int) :
    GamePacketMessage(77, type = PacketType.VARIABLE_BYTE)