package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class IfOpenOverlayEncoder : PacketEncoder<IfOpenOverlayMessage>()
{

    override fun encode(message: IfOpenOverlayMessage, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, message.id)

    }

}