package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class WorldTransferMessage(val domain: String, val worldNumber: Int, val members: Int) :
    GamePacketMessage(82, type = PacketType.VARIABLE_BYTE)