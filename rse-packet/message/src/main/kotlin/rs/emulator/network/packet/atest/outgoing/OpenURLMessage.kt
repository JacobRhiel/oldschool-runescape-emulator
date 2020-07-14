package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author javatar
 */

data class OpenURLMessage(val url: String) : GamePacketMessage(73, type = PacketType.VARIABLE_SHORT)