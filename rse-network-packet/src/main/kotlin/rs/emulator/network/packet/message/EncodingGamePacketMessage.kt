package rs.emulator.network.packet.message

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.packet.api.ActionType
import rs.emulator.packet.api.IGamePacketMessage
import rs.emulator.packet.api.IPacketEncoder
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class EncodingGamePacketMessage(opcode: Int,
                                private val encoder: IPacketEncoder<out IGamePacketMessage, out IPlayer>,
                                packetType: PacketType = PacketType.FIXED,
                                actionType: ActionType = ActionType.NONE,
                                length: Int = 0,
                                ignore: Boolean = false
) : GamePacketMessage(opcode, actionType, packetType, length, ignore)
{

    fun encode(msg: GamePacketMessage, player: Player, builder: GamePacketBuilder) = (encoder as IPacketEncoder<IGamePacketMessage, Player>).encode(msg, player, builder)

}