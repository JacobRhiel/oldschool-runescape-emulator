package rs.emulator.network.packet.decoder

import rs.emulator.entity.player.Player
import rs.emulator.packet.api.IPacketDecoder
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author Chk
 */
abstract class PacketDecoder<T : IPacketMessage> : IPacketDecoder<T, Player>
{

}