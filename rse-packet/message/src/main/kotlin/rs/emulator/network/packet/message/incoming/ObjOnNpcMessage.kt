package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ObjOnNpcMessage(
    val npcIndex: Int,
    val selectedItemId: Int,
    val selectedItemSlot: Int,
    val selectedComponentHash: Int,
    val controlPressed: Boolean
) : GamePacketMessage(99)