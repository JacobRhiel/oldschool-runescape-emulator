package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.atest.outgoing.UpdateSystemRebootMessage
import rs.emulator.network.packet.encoder.PacketEncoder
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