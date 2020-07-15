package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfMoveSubMessage(val toParent: Int,
                       val toChild: Int,
                       val fromParent: Int,
                       val fromChild: Int
) : GamePacketMessage(6)