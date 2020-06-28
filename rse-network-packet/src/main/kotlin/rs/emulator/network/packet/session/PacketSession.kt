package rs.emulator.network.packet.session

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.AttributeKey
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.entity.player.Player
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.packet.decoder.GamePacketDecoder
import rs.emulator.network.packet.encoder.GamePacketEncoder
import rs.emulator.network.packet.encoder.GamePacketMessageEncoder
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.session.NetworkSession

/**
 *
 * @author Chk
 */
class PacketSession(val channel: Channel,
                    val isaacKeys: IntArray) : KoinComponent, NetworkSession
{

    private val packetRepository: PacketRepository = get()

    val decodeRandom = IsaacRandom(isaacKeys)

    val encodeRandom = IsaacRandom(IntArray(isaacKeys.size) { isaacKeys[it] + 50 })

    val PLAYER_KEY: AttributeKey<Player> = AttributeKey.valueOf("network_player")

    init
    {

        channel.pipeline().addBefore(
            DefaultChannelHandler::class.simpleName,
            GamePacketEncoder::class.simpleName,
            GamePacketEncoder(encodeRandom)
        )

        channel.pipeline().addAfter(
            GamePacketEncoder::class.simpleName,
            GamePacketMessageEncoder::class.simpleName,
            GamePacketMessageEncoder()
        )

        channel.pipeline().addBefore(
            DefaultChannelHandler::class.simpleName,
            GamePacketDecoder::class.simpleName,
            GamePacketDecoder(decodeRandom)
        )

    }

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage)
    {

        if(msg is GamePacketMessage)
        {

            val metaData = packetRepository.fetchDecoder(msg.opcode)

            val gamePacket = metaData.decode(msg, ctx.attr(PLAYER_KEY).get())

            println("msg: $msg")

            metaData.handle(ctx.channel(), gamePacket)

            msg.release()

        }

    }

    override fun onDestroy(ctx: ChannelHandlerContext)
    {



    }

}