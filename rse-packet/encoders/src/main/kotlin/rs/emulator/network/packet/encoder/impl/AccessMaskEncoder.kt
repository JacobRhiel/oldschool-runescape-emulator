package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataOrder
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.AccessMaskMessage

/**
 *
 * @author javatar
 */

class AccessMaskEncoder : PacketEncoder<AccessMaskMessage>() {
    override fun encode(message: AccessMaskMessage, builder: GamePacketBuilder) {
        builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, (message.widgetId shl 16 and message.defChildId))
        builder.put(DataType.SHORT, message.minCs2Child)
        builder.put(DataType.SHORT, DataOrder.LITTLE, message.maxCs2Child)
        builder.put(DataType.INT, DataOrder.LITTLE, message.mask)
    }
}