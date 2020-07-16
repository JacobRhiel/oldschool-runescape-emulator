package rs.emulator.network.packet.message

import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
data class WalkHereMessage(val miniMap: Boolean = false, val destX: Int, val destZ: Int, val teleportClick: Boolean) : GamePacketMessage(66, type = PacketType.VARIABLE_BYTE)