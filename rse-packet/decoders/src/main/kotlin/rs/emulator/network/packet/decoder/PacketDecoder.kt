package rs.emulator.network.packet.decoder

import rs.emulator.entity.player.Player
import rs.emulator.packet.api.IGamePacketMessage
import rs.emulator.packet.api.IPacketDecoder

/**
 *
 * @author Chk
 */
abstract class PacketDecoder<T : IGamePacketMessage> : IPacketDecoder<T, Player>
{

}