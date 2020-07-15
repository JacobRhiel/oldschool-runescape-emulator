package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.VarpSmallMessage

/**
 *
 * @author Chk
 */
class VarpSmallEncoder : PacketEncoder<VarpSmallMessage>()
{

    override fun encode(message: VarpSmallMessage, builder: GamePacketBuilder)
    {

        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, message.value)

        builder.put(DataType.SHORT, message.id)

    }

}