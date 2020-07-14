package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetObjMessage(val componentHash: Int, val itemId: Int, val amount: Int) : GamePacketMessage(65)