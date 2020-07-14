package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetScrollPositionMessage(val componentHash: Int, val scrollY: Int) : GamePacketMessage(48)