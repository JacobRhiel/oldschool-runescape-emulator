package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class PlayMIDIJingleMessage(val jingleId: Int, val unknown: Int) : GamePacketMessage(22)