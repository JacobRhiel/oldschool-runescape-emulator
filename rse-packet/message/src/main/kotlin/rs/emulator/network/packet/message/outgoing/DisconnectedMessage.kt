package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class DisconnectedMessage(val disconnectType: Int) : GamePacketMessage(41)