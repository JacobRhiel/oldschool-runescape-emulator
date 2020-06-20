package rs.emulator.network.packet.decoder

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.network.decoder.StatefulFrameDecoder
import rs.emulator.network.packet.*
import rs.emulator.network.packet.state.PacketDecoderState
import rs.emulator.utilities.logger.warn

/**
 *
 * @author Chk
 */
class GamePacketDecoder(private val isaacRandom: IsaacRandom,
                        private val packetMetaData: PacketMetaData)
    : KoinComponent, StatefulFrameDecoder<PacketDecoderState>(PacketDecoderState.OPCODE)
{

    private val packetRepository: PacketRepository = get()

    private var opcode = 0

    private var size = 0

    private var actionType = ActionType.NONE

    private var packetType = PacketType.FIXED

    private var ignore = false

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>, state: PacketDecoderState)
    {



    }

    private fun decodeOpcode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {

        if (buf.isReadable)
        {

            opcode = buf.readUnsignedByte().toInt() - (isaacRandom.nextInt() ?: 0) and 0xFF

            val packet = packetRepository.fetchDecoder(opcode)

            if (packet == null)
            {
                warn("Channel ${ctx.channel()} sent message with no valid metadata: $opcode.")//, ctx.channel(), opcode)
                buf.skipBytes(buf.readableBytes())
                return
            }

            println("decoding packet: " + opcode)

            actionType = packet.actionType

            packetType = packet.packetType

            ignore = packet.ignore

            when (packetType)
            {
                PacketType.FIXED ->
                {

                    size = packet.length

                    if (size != 0)
                        setState(PacketDecoderState.PAYLOAD)
                    else if (!ignore)
                        out.add(
                            GamePacket(
                                opcode,
                                packet.actionType,
                                packetType,
                                Unpooled.EMPTY_BUFFER
                            )
                        )
                }

                PacketType.VARIABLE_BYTE, PacketType.VARIABLE_SHORT -> setState(PacketDecoderState.SIZE)
                else                                                                                                                            -> throw IllegalStateException("Unhandled packet type $packetType for opcode $opcode.")
            }
        }
    }

    private fun decodeLength(buf: ByteBuf, out: MutableList<Any>) {


        if (buf.isReadable)
        {
            try
            {

                size = if (packetType == PacketType.VARIABLE_SHORT) buf.readUnsignedShort() else buf.readUnsignedByte().toInt()

                if (size != 0)
                    setState(PacketDecoderState.PAYLOAD)
                else if (!ignore)
                    out.add(GamePacket(opcode, actionType, packetType, Unpooled.EMPTY_BUFFER))

            }
            catch(e: Exception)
            {
                error("Error---------- packet $opcode ")
                e.printStackTrace()
            }
        }

    }

    private fun decodePayload(buf: ByteBuf, out: MutableList<Any>)
    {

        if (buf.readableBytes() >= size)
        {

            val payload = buf.readBytes(size)

            setState(PacketDecoderState.OPCODE)

            if (!ignore)
                out.add(GamePacket(opcode, actionType, packetType, payload))

        }
    }

}