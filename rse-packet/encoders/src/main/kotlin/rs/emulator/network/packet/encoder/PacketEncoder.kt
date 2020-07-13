package rs.emulator.network.packet.encoder

import rs.emulator.packet.api.IPacketEncoder
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author Chk
 */
abstract class PacketEncoder<T : IPacketMessage> : IPacketEncoder<T>
{

}