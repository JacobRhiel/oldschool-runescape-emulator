package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class SpellOnLocMessage(
    val spellChildIndex: Int,
    val componentHash: Int,
    val x: Int,
    val y: Int,
    val locId: Int,
    val controlPressed: Boolean
) : GamePacketMessage(18)