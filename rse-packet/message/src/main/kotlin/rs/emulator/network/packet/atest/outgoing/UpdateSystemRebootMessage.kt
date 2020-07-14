package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import java.util.concurrent.TimeUnit

/**
 *
 * @author javatar
 */

data class UpdateSystemRebootMessage(val time: Int, val timeUnit: TimeUnit) : GamePacketMessage(27)