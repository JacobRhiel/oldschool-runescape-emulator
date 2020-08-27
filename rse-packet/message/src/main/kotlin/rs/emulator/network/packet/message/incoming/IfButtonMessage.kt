package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
class IfButtonMessage(
        override val opcode: Int,
        val hash: Int,
        val option: Int,
        val slot: Int,
        val item: Int
) : GamePacketMessage(57)