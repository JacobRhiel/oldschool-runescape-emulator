package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ObjOnGroundObjMessage(
    val item: Int,
    val selectedItemSlot: Int,
    val groundItemId: Int,
    val widgetItemId: Int,
    val x: Int,
    val y: Int,
    val controlPressed: Boolean
) : GamePacketMessage(32)