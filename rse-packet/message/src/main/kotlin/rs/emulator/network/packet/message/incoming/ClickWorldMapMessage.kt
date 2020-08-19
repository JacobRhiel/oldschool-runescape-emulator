package rs.emulator.network.packet.message.incoming

import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
data class ClickWorldMapMessage(val coordinateHash: Int) : GamePacketMessage(92)