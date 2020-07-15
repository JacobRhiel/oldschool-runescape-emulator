package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ToggleMinimapMessage(val toggle: Boolean) : GamePacketMessage(39)