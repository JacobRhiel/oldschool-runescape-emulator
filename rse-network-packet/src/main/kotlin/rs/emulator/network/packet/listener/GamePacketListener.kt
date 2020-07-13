package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.packet.api.IGamePacketListener
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author Chk
 */
interface GamePacketListener<T : IPacketMessage> : IGamePacketListener<T, Player>
{

}