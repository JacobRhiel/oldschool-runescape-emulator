package rs.emulator.network.packet.message.outgoing

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class VarpSmallMessage(val id: Int,
                       val value: Int) : GamePacketMessage(1)
{
}