package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class SpellOnPlayerMessage(
    val playerIndex: Int,
    val spellChildIndex: Int,
    val spellComponentHash: Int,
    val controlPressed: Boolean
) : GamePacketMessage(55)