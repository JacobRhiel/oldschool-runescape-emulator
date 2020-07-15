package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class MousePositionUpdateMessage : GamePacketMessage(74, type = PacketType.VARIABLE_BYTE)