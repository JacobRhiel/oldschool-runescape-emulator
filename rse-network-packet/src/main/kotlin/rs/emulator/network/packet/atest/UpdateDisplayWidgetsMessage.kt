package rs.emulator.network.packet.atest

import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class UpdateDisplayWidgetsMessage : GamePacketMessage(30, type = PacketType.VARIABLE_SHORT)