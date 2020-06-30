package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class GameMessageEncoder : PacketEncoder<GameMessageMessage>()
{

    override fun encode(message: GameMessageMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.putSmart(message.messageType)

        builder.put(DataType.BYTE, if(message.username.isNullOrEmpty()) 1 else 0)

        if(message.message.isNotEmpty())
        {
            val data = ByteArray(message.message.length + 1)
            System.arraycopy(message.message.toByteArray(), 0, data, 0, data.size - 1)
            data[data.size - 1] = 0
            builder.putBytes(data)
        }
        else
            builder.putBytes(ByteArray(0))

        if(message.username != null && message.username!!.isNotEmpty())
        {
            val data = ByteArray(message.username!!.length + 1)
            System.arraycopy(message.username!!.toByteArray(), 0, data, 0, data.size - 1)
            data[data.size - 1] = 0
            builder.putBytes(data)
        }
        else
            builder.putBytes(ByteArray(0))

    }

}