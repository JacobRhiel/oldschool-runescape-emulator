package rs.emulator.network.packet.encoder

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class GamePacketMessageEncoder
    : KoinComponent, MessageToMessageEncoder<GamePacketMessage>()
{

    private val packetRepository = inject<PacketRepository>()

    override fun encode(ctx: ChannelHandlerContext, msg: GamePacketMessage, out: MutableList<Any>)
    {

        val encoder = packetRepository.value.fetchEncoder(msg::class)

        val builder = GamePacketBuilder(encoder.opcode, encoder.packetType, encoder.actionType)

        encoder.encode(msg, builder)

        out.add(builder.toGamePacket())

    }

}