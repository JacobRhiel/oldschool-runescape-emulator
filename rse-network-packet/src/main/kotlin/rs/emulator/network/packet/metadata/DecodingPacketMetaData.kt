package rs.emulator.network.packet.metadata

import io.netty.channel.Channel
import rs.emulator.network.packet.ActionType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.listener.GamePacketListener
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class DecodingPacketMetaData(opcode: Int,
                             private val decoder: PacketDecoder<out GamePacketMessage>,
                             private val handler: GamePacketListener<out GamePacketMessage>,
                             packetType: PacketType = PacketType.FIXED,
                             actionType: ActionType = ActionType.NONE,
                             length: Int = 0,
                             ignore: Boolean)
    : PacketMetaData(opcode, packetType, actionType, length, ignore)
{

    fun decode(msg: GamePacketMessage) = decoder.decode(msg.opcode, GamePacketReader(msg))

    fun handle(channel: Channel, msg: GamePacketMessage) = (handler as GamePacketListener<GamePacketMessage>).handle(channel, msg)

}