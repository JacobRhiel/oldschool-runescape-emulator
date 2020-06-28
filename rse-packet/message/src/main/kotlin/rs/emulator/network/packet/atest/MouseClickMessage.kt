package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
data class MouseClickMessage(
    val timeStamp: Long,
    val lastX: Int,
    val lastY: Int,
    val button: Int
) : GamePacketMessage(2)