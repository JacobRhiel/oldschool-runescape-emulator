package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetObjMessage(val componentHash: Int, val itemId: Int, val amount: Int) : GamePacketMessage(65)