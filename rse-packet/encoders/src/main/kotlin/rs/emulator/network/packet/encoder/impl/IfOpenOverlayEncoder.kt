package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfOpenOverlayMessage

/**
 *
 * @author Chk
 */
class IfOpenOverlayEncoder : PacketEncoder<IfOpenOverlayMessage>()
{

    override fun encode(message: IfOpenOverlayMessage, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.id)

    }

}