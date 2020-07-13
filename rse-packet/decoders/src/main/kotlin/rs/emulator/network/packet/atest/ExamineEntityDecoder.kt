package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class ExamineEntityDecoder : PacketDecoder<ExamineEntityMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ExamineEntityMessage {

        val type = when (opcode) {

            8 -> 0

            26 -> 1

            96 -> 2

            else -> throw Error("Unknown entity type for opcode: $opcode.")
        }

        val id = reader.getSigned(DataType.SHORT, DataTransformation.ADD).toInt()

        return ExamineEntityMessage(type, id)

    }

}