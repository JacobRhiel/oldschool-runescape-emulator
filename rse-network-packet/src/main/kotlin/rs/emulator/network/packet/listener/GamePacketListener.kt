package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.IGamePacketListener
import rs.emulator.packet.api.IGamePacketMessage

/**
 *
 * @author Chk
 */
interface GamePacketListener<T : IGamePacketMessage> : IGamePacketListener<T, Player>
{

}