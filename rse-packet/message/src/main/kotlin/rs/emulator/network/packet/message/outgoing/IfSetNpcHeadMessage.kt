package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetNpcHeadMessage(val componentHash: Int, val npcId: Int) : GamePacketMessage(43)