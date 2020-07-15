package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

class ObjGroundActionMessage(
    val itemId: Int,
    val x: Int,
    val y: Int,
    val controlPressed: Boolean,
    val option: Int
) : GamePacketMessage(43)