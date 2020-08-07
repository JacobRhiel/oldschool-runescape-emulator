package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class AccessMaskMessage(
    val widgetId: Int,
    val defChildId: Int,
    val minCs2Child: Int,
    val maxCs2Child: Int,
    val mask: Int
) : GamePacketMessage(18)