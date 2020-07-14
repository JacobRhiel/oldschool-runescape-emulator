package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class ClientStatisticsMessage(val unknown1: Int, val unknown2: Int) : GamePacketMessage(69)