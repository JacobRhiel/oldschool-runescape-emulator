package rs.emulator.network.packet

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