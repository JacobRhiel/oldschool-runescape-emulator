package rs.emulator.packet.api

import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author Chk
 */
interface IGamePacketListener<T : IPacketMessage, P : IPlayer>
{

    fun handle(player: P, message: T)

}