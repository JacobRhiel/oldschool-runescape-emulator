package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UpdateSystemRebootMessage
import java.util.concurrent.TimeUnit

/**
 *
 * @author javatar
 */

class UpdateSystemRebootEncoder : PacketEncoder<UpdateSystemRebootMessage>() {
    override fun encode(message: UpdateSystemRebootMessage, builder: GamePacketBuilder) {
        var time = message.time
        when (message.timeUnit) {
            TimeUnit.MINUTES -> time = message.time * 60
            TimeUnit.HOURS -> time = (message.time * 60) * 60
            else -> {
            }
        }
        builder.put(DataType.SHORT, time)
    }
}