package rs.emulator.network.packet.encoder

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.ext.toGamePacket
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.packet.session.PacketSession

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

        val builder = GamePacketBuilder(
            encoder.opcode,
            encoder.type,
            encoder.action
        )

        val session = ctx.attr(SESSION_KEY).get() as PacketSession

        encoder.encode(msg, ctx.attr(session.PLAYER_KEY).get(), builder)

        out.add(builder.toGamePacket())

    }

}