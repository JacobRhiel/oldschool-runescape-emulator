package rs.emulator.network.packet.encoder

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.IGamePacketMessage
import rs.emulator.packet.api.IPacketEncoder

/**
 *
 * @author Chk
 */
abstract class PacketEncoder<T : IGamePacketMessage> : IPacketEncoder<T, Player>
{

}