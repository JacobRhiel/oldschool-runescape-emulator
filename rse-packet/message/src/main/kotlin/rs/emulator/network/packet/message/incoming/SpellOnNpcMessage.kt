package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class SpellOnNpcMessage(
    val npcIndex: Int,
    val spellChildIndex: Int,
    val spellComponentHash: Int,
    val controlPressed: Boolean
) : GamePacketMessage(38)