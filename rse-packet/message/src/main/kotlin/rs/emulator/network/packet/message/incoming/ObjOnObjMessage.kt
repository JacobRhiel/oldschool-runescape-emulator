package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ObjOnObjMessage(
    val itemId: Int,
    val slot: Int,
    val selectedItemId: Int,
    val selectedSlot: Int,
    val componentHash: Int,
    val selectedComponentHash: Int
) : GamePacketMessage(63)