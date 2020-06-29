package rs.emulator.packet.api

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.network.packet.GamePacketReader

/**
 *
 * @author Chk
 */
interface IPacketDecoder<T : IGamePacketMessage, P : IPlayer> : IGamePacketCodec
{

    fun decode(opcode: Int, player: P, reader: GamePacketReader): T

}