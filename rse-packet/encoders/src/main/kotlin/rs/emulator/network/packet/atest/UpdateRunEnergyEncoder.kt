package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.UpdateRunEnergyMessage
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author javatar
 */

class UpdateRunEnergyEncoder : PacketEncoder<UpdateRunEnergyMessage>() {
    override fun encode(message: UpdateRunEnergyMessage, builder: GamePacketBuilder) {
        builder.put(DataType.BYTE, message.energy)
    }
}