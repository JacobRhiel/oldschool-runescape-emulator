package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UpdateRunEnergyMessage

/**
 *
 * @author javatar
 */

class UpdateRunEnergyEncoder : PacketEncoder<UpdateRunEnergyMessage>() {
    override fun encode(message: UpdateRunEnergyMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.energy)
    }
}