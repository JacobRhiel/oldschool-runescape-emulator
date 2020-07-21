package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class SpellOnGroundObjMessage(
    val itemId: Int,
    val x: Int,
    val y: Int,
    val spellChildIndex: Int,
    val spellComponentHash: Int,
    val controlPressed: Boolean
) : GamePacketMessage(60)