package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.LocActionMessage

/**
 *
 * @author javatar
 */

class LocActionDecoder : PacketDecoder<LocActionMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): LocActionMessage {

        var locId = -1
        var x = 0
        var y = 0
        var controlPressed = false
        var option = -1

        when (opcode) {

            88 -> {
                y = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                controlPressed = reader.getUnsigned(
                    DataType.BYTE,
                    DataTransformation.ADD
                ).toInt() == 1
                x = reader.getUnsigned(
                    DataType.SHORT
                ).toInt()
                locId = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
                option = 1
            }
            41 -> {
                controlPressed = reader.getUnsigned(
                    DataType.BYTE,
                    DataTransformation.SUBTRACT
                ).toInt() == 1
                y = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
                locId = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                x = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
                option = 2
            }
            72 -> {
                x = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE
                ).toInt()
                y = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE
                ).toInt()
                locId = reader.getUnsigned(
                    DataType.SHORT
                ).toInt()
                controlPressed = reader.getUnsigned(
                    DataType.BYTE,
                    DataTransformation.ADD
                ).toInt() == 1
                option = 3
            }
            29 -> {
                x = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                controlPressed = reader.getUnsigned(
                    DataType.BYTE,
                    DataTransformation.SUBTRACT
                ).toInt() == 1
                locId = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE,
                    DataTransformation.ADD
                ).toInt()
                y = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE
                ).toInt()
                option = 4
            }
            30 -> {
                locId = reader.getUnsigned(
                    DataType.SHORT,
                    DataOrder.LITTLE
                ).toInt()
                y = reader.getUnsigned(
                    DataType.SHORT,
                    DataTransformation.ADD
                ).toInt()
                x = reader.getUnsigned(
                    DataType.SHORT
                ).toInt()
                controlPressed = reader.getUnsigned(
                    DataType.BYTE,
                    DataTransformation.ADD
                ).toInt() == 1
                option = 5
            }
        }
        return LocActionMessage(locId, x, y, controlPressed, option)
    }
}