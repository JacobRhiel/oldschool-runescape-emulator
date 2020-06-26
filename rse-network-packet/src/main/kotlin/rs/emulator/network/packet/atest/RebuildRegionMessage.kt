package rs.emulator.network.packet.atest

import rs.emulator.network.packet.PacketType
import rs.emulator.network.packet.message.GamePacketMessage

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
) : GamePacketMessage(17, type = PacketType.VARIABLE_SHORT)
