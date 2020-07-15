package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ObjGroundActionMessage

/**
 *
 * @author javatar
 */

class ObjGroundActionDecoder : PacketDecoder<ObjGroundActionMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ObjGroundActionMessage {
        var itemId = 0
        var x = 0
        var y = 0
        var controlPressed = false
        var option = -1
        when (opcode) {

            43 -> {
                x = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
                y = reader.getUnsigned(DataType.SHORT).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() == 1
                itemId = reader.getUnsigned(DataType.SHORT).toInt()
                option = 1
            }
            39 -> {
                x = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() == 1
                y = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                itemId = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                option = 2
            }
            57 -> {
                itemId = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD).toInt() == 1
                y = reader.getUnsigned(DataType.SHORT).toInt()
                x = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                option = 3
            }
            42 -> {
                x = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD).toInt() == 1
                y = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                itemId = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
                option = 4
            }
            1 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT).toInt() == 1
                x = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                y = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                itemId = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                option = 5
            }

        }
        return ObjGroundActionMessage(itemId, x, y, controlPressed, option)
    }
}