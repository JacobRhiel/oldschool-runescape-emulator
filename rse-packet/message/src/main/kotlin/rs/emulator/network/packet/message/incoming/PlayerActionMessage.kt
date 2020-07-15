package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class PlayerActionMessage(val playerIndex: Int, val option: Int, val controlPressed: Boolean) :
    GamePacketMessage(81)