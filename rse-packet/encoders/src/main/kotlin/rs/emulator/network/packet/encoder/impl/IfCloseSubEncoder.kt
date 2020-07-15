package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.IfCloseSubMessage

/**
 *
 * @author Chk
 */
class IfCloseSubEncoder : PacketEncoder<IfCloseSubMessage>()
{

    override fun encode(message: IfCloseSubMessage, builder: GamePacketBuilder)
    {

        builder.put(DataType.INT, (message.parent shl 16) or message.child)

    }

}