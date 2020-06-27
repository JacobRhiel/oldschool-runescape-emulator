package rs.emulator.network.packet.atest

import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class ConsoleCommandMessage(val args: String) : GamePacketMessage(93, type = PacketType.VARIABLE_BYTE)
{
}