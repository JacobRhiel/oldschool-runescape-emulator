package rs.emulator.network.packet.message

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.listener.GamePacketListener
import rs.emulator.packet.api.*

/**
 *
 * @author Chk
 */
class DecodingGamePacketMessage(
    val opcode: Int,
    private val decoder: IPacketDecoder<*>,
    private val handler: IGamePacketListener<*, Player>,
    val type: PacketType = PacketType.FIXED,
    val action: ActionType = ActionType.NONE,
    val length: Int = 0,
    val ignore: Boolean
) {

    fun decode(msg: GamePacketMessage) = decoder.decode(
        msg.opcode,
        GamePacketReader(msg.payload)
    )

    fun handle(channel: Channel, player: Player, msg: IPacketMessage) =
        (handler as GamePacketListener<IPacketMessage>).handle(channel, player, msg)

}