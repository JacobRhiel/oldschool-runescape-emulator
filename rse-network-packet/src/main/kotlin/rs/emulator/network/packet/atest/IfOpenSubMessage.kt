package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfOpenSubMessage(val parent: Int, val child: Int, val component: Int, val interType: Int) : GamePacketMessage(64)
{
}