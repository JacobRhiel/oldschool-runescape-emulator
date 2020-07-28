package rs.emulator.network.packet.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.encryption.isaac.IsaacRandom
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.packet.api.GamePacket
import rs.emulator.packet.api.PacketType
import rs.emulator.utilities.logger.logger
import java.text.DecimalFormat

/**
 *
 * @author Chk
 */
class GamePacketEncoder(private val isaac: IsaacRandom) : KoinComponent, MessageToByteEncoder<GamePacket>() {

    private val packetRepository: PacketRepository = get()

    override fun encode(ctx: ChannelHandlerContext, msg: GamePacket, out: ByteBuf) {

        if (msg.type == PacketType.VARIABLE_BYTE && msg.length >= 256) {
            logger().error(
                "Message length {} too long for 'variable-byte' packet on channel {}.",
                DecimalFormat().format(msg.length),
                ctx.channel()
            )
            return
        } else if (msg.type == PacketType.VARIABLE_SHORT && msg.length >= 65536) {
            logger().error(
                "Message length {} too long for 'variable-short' packet on channel {}.",
                DecimalFormat().format(msg.length),
                ctx.channel()
            )
            return
        }

        out.writeByte((msg.opcode + (isaac.nextInt())) and 0xFF)

        when (msg.type) {

            PacketType.VARIABLE_BYTE -> out.writeByte(msg.length)

            PacketType.VARIABLE_SHORT -> out.writeShort(msg.length)

            else -> {
            }

        }

        //println("Encoding packet: ${msg.javaClass.simpleName}. ${msg.opcode}")

        out.writeBytes(msg.payload)

        msg.release()

    }

}