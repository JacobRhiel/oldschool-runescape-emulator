package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfButtonMessage(val hash: Int,
                           val option: Int,
                           val slot: Int,
                           val item: Int
) : GamePacketMessage(57)
{
}