package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class IfOpenSubEncoder : PacketEncoder<IfOpenSubMessage>()
{

    override fun encode(message: IfOpenSubMessage, builder: GamePacketBuilder)
    {

        builder.put(DataType.BYTE, message.interType)

        builder.put(DataType.SHORT, DataTransformation.ADD, message.component)

        builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.parent shl 16) or message.child)

    }

}