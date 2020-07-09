package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
data class ExamineEntityMessage(val entityType: Int,
                           val id: Int
) : GamePacketMessage(8)