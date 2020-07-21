package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.LegacyObjActionMessage

/**
 *
 * @author javatar
 */

class LegacyObjActionDecoder : PacketDecoder<LegacyObjActionMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): LegacyObjActionMessage {
        var itemId = -1
        var slot = -1
        var componentHash = -1

        when (opcode) {

            64 -> {
                componentHash = reader.getUnsigned(
                    DataType.INT,
                    DataOrder.INVERSED_MIDDLE
                ).toInt()
                itemId = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                slot = reader.getUnsigned(DataType.SHORT).toInt()
            }
            36 -> {
                itemId = reader.getUnsigned(
                    DataType.SHORT
                ).toInt()
                componentHash = reader.getUnsigned(DataType.INT).toInt()
                slot = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
            }
            52 -> {
                componentHash = reader.getUnsigned(DataType.INT).toInt()
                itemId = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
                slot = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
            }
            67 -> {

                componentHash = reader.getUnsigned(DataType.INT).toInt()
                itemId = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                slot = reader.getUnsigned(DataType.SHORT).toInt()

            }
            11 -> {
                itemId = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE
                ).toInt()
                componentHash = reader.getUnsigned(DataType.INT).toInt()
                slot = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
            }

        }
        return LegacyObjActionMessage(itemId, slot, componentHash)
    }
}