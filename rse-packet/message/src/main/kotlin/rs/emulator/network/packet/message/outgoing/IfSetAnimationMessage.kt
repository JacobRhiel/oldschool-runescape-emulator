package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetAnimationMessage(val componentHash: Int, val animationID: Int) : GamePacketMessage(61)