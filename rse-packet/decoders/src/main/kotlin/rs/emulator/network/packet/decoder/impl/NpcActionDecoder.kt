package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.NpcActionMessage

/**
 *
 * @author javatar
 */

class NpcActionDecoder : PacketDecoder<NpcActionMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): NpcActionMessage {
        var npcIndex = -1
        var controlPressed = false
        var option = -1

        when (opcode) {

            33 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT).toInt() == 1
                npcIndex = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                option = 1
            }
            10 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() == 1
                npcIndex = reader.getUnsigned(DataType.SHORT).toInt()
                option = 2
            }
            19 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT).toInt() == 1
                npcIndex = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
                option = 3
            }
            97 -> {
                npcIndex = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE).toInt() == 1
                option = 4
            }
            37 -> {
                npcIndex = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE).toInt() == 1
                option = 5
            }

        }

        return NpcActionMessage(npcIndex, option, controlPressed)
    }
}