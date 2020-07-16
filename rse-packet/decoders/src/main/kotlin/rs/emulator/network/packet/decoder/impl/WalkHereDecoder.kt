package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.message.WalkHereMessage
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class WalkHereDecoder : PacketDecoder<WalkHereMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): WalkHereMessage
    {

        var x = 0

        var z = 0

        var teleportClick = false

        when(opcode)
        {

            48 ->
            {

                z = reader.getSigned(DataType.SHORT).toInt()

                x = reader.getSigned(DataType.SHORT).toInt()

                teleportClick = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() != 0

                reader.skipBytes(13)

            }

            66 ->
            {

                z = reader.getSigned(DataType.SHORT).toInt()

                x = reader.getSigned(DataType.SHORT).toInt()

                teleportClick = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() != 0

            }

        }

        return WalkHereMessage(opcode == 48, x, z, teleportClick)

    }

}