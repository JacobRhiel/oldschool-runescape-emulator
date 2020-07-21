package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class LocActionMessage(val locId: Int, val x: Int, val y: Int, val controlPressed: Boolean, val option: Int) :
    GamePacketMessage(88)