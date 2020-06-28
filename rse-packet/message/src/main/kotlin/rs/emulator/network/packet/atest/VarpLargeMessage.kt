package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class VarpLargeMessage(val id: Int,
                       val value: Int) : GamePacketMessage(18)
{
}