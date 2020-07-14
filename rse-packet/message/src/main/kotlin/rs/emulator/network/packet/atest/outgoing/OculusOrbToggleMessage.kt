package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class OculusOrbToggleMessage(val toggle: Boolean) : GamePacketMessage(35)