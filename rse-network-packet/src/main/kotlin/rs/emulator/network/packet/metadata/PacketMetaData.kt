package rs.emulator.network.packet.metadata

import rs.emulator.network.packet.ActionType
import rs.emulator.network.packet.PacketType

/**
 *
 * @author Chk
 */
open class PacketMetaData(val opcode: Int,
                          val packetType: PacketType = PacketType.FIXED,
                          val actionType: ActionType = ActionType.NONE,
                          val length: Int = 0,
                          val ignore: Boolean
)