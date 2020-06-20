package rs.emulator.network.packet.encoder

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.PacketRepository
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class GamePacketEncoder
    : KoinComponent, MessageToMessageEncoder<GamePacketMessage>()
{

    private val packetRepository: PacketRepository = get()

    override fun encode(ctx: ChannelHandlerContext, msg: GamePacketMessage, out: MutableList<Any>)
    {

        val encoder = packetRepository.fetchEncoder(msg.opcode)

        if(encoder == null)
        {

            println("null encoder for opcode: ${msg.opcode}")

            return

        }

        val builder = GamePacketBuilder(encoder.opcode, encoder.packetType, encoder.actionType)



    }

}