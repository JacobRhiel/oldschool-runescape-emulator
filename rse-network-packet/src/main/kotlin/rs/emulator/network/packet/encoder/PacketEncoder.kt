package rs.emulator.network.packet.encoder

import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
abstract class PacketEncoder<T : GamePacketMessage>
{

    abstract fun encode(message: T, builder: GamePacketBuilder)

}