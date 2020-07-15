package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetHideMessage(val componentHash: Int, val hidden: Boolean) : GamePacketMessage(57)