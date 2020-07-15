package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfMoveSubMessage

/**
 *
 * @author Chk
 */
class IfMoveSubEncoder : PacketEncoder<IfMoveSubMessage>()
{

    override fun encode(message: IfMoveSubMessage, builder: GamePacketBuilder)
    {

        val toHash = (message.toParent shl 16) or message.toChild

        val fromHash = (message.fromParent shl 16) or message.fromChild

        builder.put(DataType.INT, toHash)

        builder.put(DataType.INT, DataOrder.LITTLE, fromHash)

    }

}