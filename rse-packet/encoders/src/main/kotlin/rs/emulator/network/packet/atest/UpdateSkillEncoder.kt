package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class UpdateSkillEncoder : PacketEncoder<UpdateSkillMessage>() {
    override fun encode(message: UpdateSkillMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, DataTransformation.SUBTRACT, message.level)
        builder.put(DataType.INT, DataOrder.LITTLE, message.experience)
        builder.put(DataType.BYTE, DataTransformation.NEGATE, message.id)
    }
}