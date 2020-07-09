package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
data class ObjActionMessage(val item: Int,
                            val option: Int,
                            val componentHash: Int
) : GamePacketMessage(7)