package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ObjOnPlayerMessage(
    val targetPlayerIndex: Int,
    val itemId: Int,
    val componentHash: Int,
    val selectedItemSlot: Int,
    val controlPressed: Boolean
) : GamePacketMessage(9)