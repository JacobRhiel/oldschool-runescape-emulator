package rs.emulator.packet.api

import rs.emulator.network.packet.GamePacketBuilder

/**
 *
 * @author Chk
 */
interface IPacketEncoder<T : IPacketMessage> : IGamePacketCodec
{

    fun encode(message: T, builder: GamePacketBuilder)

}