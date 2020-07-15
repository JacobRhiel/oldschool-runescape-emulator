package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class UpdateDisplayWidgetsMessage : GamePacketMessage(30, type = PacketType.VARIABLE_SHORT)