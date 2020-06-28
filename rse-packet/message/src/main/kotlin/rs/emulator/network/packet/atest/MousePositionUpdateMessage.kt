package rs.emulator.network.packet.atest

import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class MousePositionUpdateMessage : GamePacketMessage(74, type = PacketType.VARIABLE_BYTE)