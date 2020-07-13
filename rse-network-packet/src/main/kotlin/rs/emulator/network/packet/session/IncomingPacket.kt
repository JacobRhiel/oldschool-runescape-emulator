package rs.emulator.network.packet.session

import rs.emulator.network.packet.message.DecodingGamePacketMessage
import rs.emulator.packet.api.IPacketMessage

/**
 *
 * @author javatar
 */

data class IncomingPacket(val metaData: DecodingGamePacketMessage, val msg: IPacketMessage)