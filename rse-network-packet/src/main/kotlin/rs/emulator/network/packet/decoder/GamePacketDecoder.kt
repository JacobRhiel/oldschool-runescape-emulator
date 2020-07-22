package rs.emulator.network.packet.decoder

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.network.decoder.StatefulFrameDecoder
import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.packet.state.PacketDecoderState
import rs.emulator.network.packet.state.PacketDecoderState.*
import rs.emulator.packet.api.ActionType
import rs.emulator.packet.api.PacketType
import rs.emulator.utilities.logger.warn

/**
 *
 * @author Chk
 */
class GamePacketDecoder(private val isaacRandom: IsaacRandom)
    : KoinComponent, StatefulFrameDecoder<PacketDecoderState>(OPCODE)
{

    private val packetRepository: PacketRepository = get()

    private var opcode = 0

    private var size = 0

    private var actionType = ActionType.NONE

    private var packetType = PacketType.FIXED

    private var ignore = false

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>, state: PacketDecoderState)
    {

        when (state)
        {

            OPCODE -> decodeOpcode(ctx, buf, out)

            LENGTH -> decodeLength(buf, out)

            PAYLOAD -> decodePayload(buf, out)

        }

    }

    private fun decodeOpcode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>)
    {

        if (buf.isReadable)
        {

            opcode = buf.readUnsignedByte().toInt() - isaacRandom.nextInt() and 0xFF

            val packet = packetRepository.fetchDecoder(opcode)

            actionType = packet.action

            packetType = packet.type

            ignore = packet.ignore

            when (packetType)
            {
                PacketType.FIXED ->
                {

                    size = packet.length

                    if (size != 0)
                        setState(PAYLOAD)
                    else if (!ignore)
                        out.add(
                            GamePacketMessage(
                                opcode,
                                packet.action,
                                packetType,
                                payload = Unpooled.EMPTY_BUFFER
                            )
                        )
                }

                PacketType.VARIABLE_BYTE, PacketType.VARIABLE_SHORT -> setState(LENGTH)
                else -> throw IllegalStateException("Unhandled packet type $packetType for opcode $opcode.")
            }
        }
    }

    private fun decodeLength(buf: ByteBuf, out: MutableList<Any>)
    {

        if (buf.isReadable)
        {
            try
            {

                size = if (packetType == PacketType.VARIABLE_SHORT) buf.readUnsignedShort() else buf.readUnsignedByte().toInt()

                if (size != 0)
                    setState(PAYLOAD)
                else if (!ignore)
                    out.add(
                        GamePacketMessage(
                            opcode,
                            actionType,
                            packetType,
                            payload = Unpooled.EMPTY_BUFFER
                        )
                    )

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

            setState(OPCODE)

            if (!ignore)
                out.add(
                    GamePacketMessage(
                        opcode,
                        actionType,
                        packetType,
                        payload = payload
                    )
                )

        }
    }

}