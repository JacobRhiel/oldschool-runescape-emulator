package rs.emulator.network.packet.session

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.packet.decoder.GamePacketDecoder
import rs.emulator.network.packet.encoder.GamePacketEncoder
import rs.emulator.network.packet.encoder.GamePacketMessageEncoder
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.session.NetworkSession
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author Chk
 */
class PacketSession(
    val channel: Channel,
    val isaacKeys: IntArray,
    var compositeDisposable: CompositeDisposable
) : KoinComponent, NetworkSession {

    private val packetRepository: PacketRepository = get()

    val decodeRandom = IsaacRandom(isaacKeys)

    val encodeRandom = IsaacRandom(IntArray(isaacKeys.size) { isaacKeys[it] + 50 })

    val incomingPackets = PublishProcessor.create<IncomingPacket>()
    val outgoingPackets = PublishProcessor.create<IPacketMessage>()

    val composite = CompositeDisposable()

    init {

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

    override fun onMessage(ctx: ChannelHandlerContext, msg: NetworkMessage) {

        if (msg is GamePacketMessage) {

            val metaData = packetRepository.fetchDecoder(msg.opcode)

            val gamePacket = metaData.decode(msg)

            incomingPackets.offer(IncomingPacket(metaData, gamePacket))

            msg.release()

        }

    }

    override fun onDestroy(ctx: ChannelHandlerContext) {

        //todo: remove player from WorldRepository.players?

        compositeDisposable.dispose()

    }

}