package rs.emulator.network.packet.atest

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

class UpdateSkillMessage(val id: Int, val level: Int, val experience: Int) : GamePacketMessage(12)