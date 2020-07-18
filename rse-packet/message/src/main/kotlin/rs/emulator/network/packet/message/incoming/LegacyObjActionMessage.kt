package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author javatar
 */

data class LegacyObjActionMessage(val itemId: Int, val slot: Int, val componentHash: Int) : GamePacketMessage(64)