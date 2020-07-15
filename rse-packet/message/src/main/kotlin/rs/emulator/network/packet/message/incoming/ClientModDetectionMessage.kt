package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class ClientModDetectionMessage : GamePacketMessage(47, type = PacketType.VARIABLE_BYTE)