package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class HintArrowMessage(val hintArrayType: Int, val entityIndex: Int = -1, val x: Int, val z: Int, val y: Int) :
    GamePacketMessage(28)