package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class PrivateMessageMessage(val message: String, val nameHash: Long, val value: Long) :
    GamePacketMessage(2, type = PacketType.VARIABLE_SHORT)