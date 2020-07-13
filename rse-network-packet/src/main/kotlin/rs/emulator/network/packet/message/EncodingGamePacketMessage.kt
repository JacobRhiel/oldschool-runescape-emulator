package rs.emulator.network.packet.message

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.*

/**
 *
 * @author Chk
 */
class EncodingGamePacketMessage(val opcode: Int,
                                private val encoder: IPacketEncoder<*>,
                                val type: PacketType = PacketType.FIXED,
                                val action: ActionType = ActionType.NONE,
                                val length: Int = 0,
                                val ignore: Boolean = false
)
{

    fun encode(msg: IPacketMessage, builder: GamePacketBuilder) = (encoder as IPacketEncoder<IPacketMessage>).encode(msg, builder)

}