package rs.emulator.network.packet.decoder.impl

import org.koin.core.KoinComponent
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.PublicChatMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class PublicMessageDecoder : PacketDecoder<PublicChatMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): PublicChatMessage
    {

        val type = reader.getSigned(DataType.BYTE).toInt()

        val color = reader.getSigned(DataType.BYTE).toInt()

        val effect = reader.getSigned(DataType.BYTE).toInt()

        val length = reader.unsignedSmart

        val data = ByteArray(reader.readableBytes)

        reader.getBytes(data)

        return PublicChatMessage(type, color, effect, length, data)

    }

}