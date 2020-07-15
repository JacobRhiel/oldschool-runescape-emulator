package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.IfSwapItemMessage

/**
 *
 * @author javatar
 */

class IfSwapItemDecoder : PacketDecoder<IfSwapItemMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): IfSwapItemMessage {
        return IfSwapItemMessage(
            reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT).toInt(),
            reader.getUnsigned(DataType.INT).toInt(),
            reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt(),
            reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD).toInt()
        )
    }
}