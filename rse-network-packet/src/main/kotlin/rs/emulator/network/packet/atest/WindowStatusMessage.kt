package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
data class WindowStatusMessage(val mode: Int, val width: Int, val height: Int) : GamePacketMessage(52)