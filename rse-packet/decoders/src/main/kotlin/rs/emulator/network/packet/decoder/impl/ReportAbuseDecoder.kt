package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ReportAbuseMessage

/**
 *
 * @author javatar
 */

class ReportAbuseDecoder : PacketDecoder<ReportAbuseMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): ReportAbuseMessage {
        val name = reader.string
        val abuseType = reader.getUnsigned(DataType.BYTE).toInt() + 1
        val isStaff = reader.getUnsigned(DataType.BYTE).toInt() == 1
        return ReportAbuseMessage(name, abuseType, isStaff)
    }
}