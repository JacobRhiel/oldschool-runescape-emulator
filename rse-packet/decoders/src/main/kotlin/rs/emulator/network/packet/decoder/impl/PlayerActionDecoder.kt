package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.PlayerActionMessage

/**
 *
 * @author javatar
 */

class PlayerActionDecoder : PacketDecoder<PlayerActionMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): PlayerActionMessage {
        var playerIndex = 0
        var controlPressed = false
        var option = -1
        when (opcode) {
            81 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE).toInt() == 1
                playerIndex = reader.getUnsigned(DataType.SHORT).toInt()
                option = 1
            }
            87, 4 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD).toInt() == 1
                playerIndex = reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE).toInt()
                option = if (opcode == 87) 2 else 3
            }
            21 -> {
                playerIndex = reader.getUnsigned(DataType.SHORT).toInt()
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() == 1
                option = 4
            }
            94 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD).toInt() == 1
                playerIndex = reader.getUnsigned(DataType.SHORT).toInt()
                option = 5
            }
            57 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.ADD).toInt() == 1
                playerIndex = reader.getUnsigned(DataType.SHORT, DataTransformation.ADD).toInt()
                option = 6
            }
            51 -> {
                controlPressed = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE).toInt() == 1
                playerIndex = reader.getUnsigned(DataType.SHORT).toInt()
                option = 7
            }

        }
        return PlayerActionMessage(playerIndex, option, controlPressed)
    }
}