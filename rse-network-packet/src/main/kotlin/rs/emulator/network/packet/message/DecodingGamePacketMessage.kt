package rs.emulator.network.packet.message

import io.netty.channel.Channel
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.packet.api.PacketType
import rs.emulator.network.packet.listener.GamePacketListener
import rs.emulator.packet.api.*

/**
 *
 * @author Chk
 */
class DecodingGamePacketMessage(opcode: Int,
                                private val decoder: IPacketDecoder<out IGamePacketMessage, Player>,
                                private val handler: IGamePacketListener<out IGamePacketMessage, Player>,
                                packetType: PacketType = PacketType.FIXED,
                                actionType: ActionType = ActionType.NONE,
                                length: Int = 0,
                                ignore: Boolean
) : GamePacketMessage(opcode, actionType, packetType, length, ignore)
{

    fun decode(msg: GamePacketMessage, player: Player) = decoder.decode(msg.opcode, player,
        GamePacketReader(msg.payload)
    )

    fun handle(channel: Channel, player: Player, msg: IGamePacketMessage) = (handler as GamePacketListener<IGamePacketMessage>).handle(channel, player, msg)

}