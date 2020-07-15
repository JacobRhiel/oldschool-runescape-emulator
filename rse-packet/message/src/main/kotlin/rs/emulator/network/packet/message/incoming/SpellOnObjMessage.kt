package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class SpellOnObjMessage(
    val itemId: Int,
    val slot: Int,
    val componentHash: Int,
    val spellComponentHash: Int,
    val spellChildIndex: Int
) : GamePacketMessage(54)