package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class VarpSmallEncoder : PacketEncoder<VarpSmallMessage>()
{

    override fun encode(message: VarpSmallMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, message.value)

        builder.put(DataType.SHORT, message.id)

    }

}