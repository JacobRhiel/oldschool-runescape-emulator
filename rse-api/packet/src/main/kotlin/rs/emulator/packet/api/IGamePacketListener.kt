package rs.emulator.packet.api

import io.netty.channel.Channel
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author Chk
 */
interface IGamePacketListener<T : IGamePacketMessage, P : IPlayer>
{

    fun handle(channel: Channel, player: P, message: T)

}