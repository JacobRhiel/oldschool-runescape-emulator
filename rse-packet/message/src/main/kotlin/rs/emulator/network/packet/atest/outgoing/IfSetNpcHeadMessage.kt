package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetNpcHeadMessage(val componentHash: Int, val npcId: Int) : GamePacketMessage(43)