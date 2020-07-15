package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ObjActionMessage

/**
 *
 * @author Chk
 */
class ObjActionDecoder : PacketDecoder<ObjActionMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ObjActionMessage {

        var item = 0

        var slot = 0

        var componentHash = 0

        when (opcode) {

            7 ->
            {

                item = reader.getSigned(DataType.SHORT, DataTransformation.ADD).toInt()

                componentHash = reader.getSigned(DataType.INT, DataOrder.INVERSED_MIDDLE).toInt()

                slot = reader.getSigned(DataType.SHORT).toInt()

            }

            61 ->
            {

                item = reader.getSigned(DataType.SHORT, DataTransformation.ADD).toInt()

                slot = reader.getSigned(DataType.SHORT).toInt()

                componentHash = reader.getSigned(DataType.INT).toInt()

            }

            74 ->
            {

                componentHash = reader.getSigned(DataType.INT, DataOrder.MIDDLE).toInt()

                slot = reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()

                item = reader.getSigned(DataType.SHORT, DataOrder.LITTLE).toInt()

            }

            86 ->
            {

                componentHash = reader.getSigned(DataType.INT, DataOrder.LITTLE).toInt()

                slot = reader.getSigned(DataType.SHORT).toInt()

                item = reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()

            }

            93 ->
            {

                item = reader.getSigned(DataType.SHORT).toInt()

                componentHash = reader.getSigned(DataType.INT, DataOrder.MIDDLE).toInt()

                slot = reader.getSigned(DataType.SHORT, DataOrder.LITTLE).toInt()

            }

        }

        return ObjActionMessage(item, slot, componentHash)

    }

}