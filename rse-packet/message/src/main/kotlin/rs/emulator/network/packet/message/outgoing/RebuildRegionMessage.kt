package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage
import rs.emulator.packet.api.PacketType

/**
 *
 * @author Chk
 */
class RebuildRegionMessage(
    val login: Boolean,
    val index: Int,
    val x: Int,
    val z: Int,
    val height: Int = 0,
    val tileHash: Int
) : GamePacketMessage(21, type = PacketType.VARIABLE_SHORT)
