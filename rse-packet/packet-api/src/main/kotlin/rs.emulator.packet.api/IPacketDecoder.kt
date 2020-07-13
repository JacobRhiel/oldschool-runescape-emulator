package rs.emulator.packet.api

import rs.emulator.network.packet.GamePacketReader

/**
 *
 * @author Chk
 */
interface IPacketDecoder<T : IPacketMessage> : IGamePacketCodec {

    fun decode(opcode: Int, reader: GamePacketReader): T

}