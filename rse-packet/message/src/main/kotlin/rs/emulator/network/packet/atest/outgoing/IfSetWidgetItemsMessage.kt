package rs.emulator.network.packet.atest.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class IfSetWidgetItemsMessage(val componentHash: Int) : GamePacketMessage(4)