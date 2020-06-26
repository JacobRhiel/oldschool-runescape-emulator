package rs.emulator.network.packet.decoder

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
abstract class PacketDecoder<T : GamePacketMessage>
{

    abstract fun decode(opcode: Int, reader: GamePacketReader): T

}