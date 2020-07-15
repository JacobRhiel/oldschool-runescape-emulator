package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetModelMessage(val componentHash: Int, val modelID: Int) : GamePacketMessage(7)