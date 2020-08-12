package rs.emulator.network.packet.message

import rs.emulator.packet.api.GamePacketHandler
import rs.emulator.packet.api.IPacketMessage


/**
 *
 * @author javatar
 */

data class IncomingPacket(val handler: GamePacketHandler, val msg: IPacketMessage)