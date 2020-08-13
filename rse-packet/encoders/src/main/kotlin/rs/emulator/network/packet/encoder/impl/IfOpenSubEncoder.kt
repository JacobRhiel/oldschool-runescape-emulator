package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfOpenSubMessage

/**
 *
 * @author Chk
 */
class IfOpenSubEncoder : PacketEncoder<IfOpenSubMessage>()
{

    override fun encode(message: IfOpenSubMessage, builder: GamePacketBuilder)
    {

        if((message.parent shl 16) == -1) {

            println("Fuck! ${message.parent}")

        }

        builder.put(DataType.BYTE, message.interType)

        builder.put(DataType.SHORT, DataTransformation.ADD, message.component)

        builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.parent shl 16) or message.child)

    }

}