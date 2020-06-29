package rs.emulator.packet.api

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
interface IPacketEncoder<T : IGamePacketMessage, P : IPlayer> : IGamePacketCodec
{

    fun encode(message: T, player: P, builder: GamePacketBuilder)

}