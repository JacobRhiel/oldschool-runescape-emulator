package rs.emulator.network.packet.message

import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
data class GamePacketMessage(val opcode: Int) : NetworkMessage