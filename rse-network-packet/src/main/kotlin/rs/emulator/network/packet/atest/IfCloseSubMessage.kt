package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfCloseSubMessage(val parent: Int,
                        val child: Int
) : GamePacketMessage(70)