package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSwapItemMessage(val unknown: Int, val componentHash: Int, val toSlot: Int, val fromSlot: Int) :
    GamePacketMessage(15)