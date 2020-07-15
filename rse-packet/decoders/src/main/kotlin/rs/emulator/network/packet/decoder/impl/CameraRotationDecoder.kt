package rs.emulator.network.packet.decoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.CameraRotationMessage

/**
 *
 * @author javatar
 */

class CameraRotationDecoder : PacketDecoder<CameraRotationMessage>() {
    override fun decode(opcode: Int, reader: GamePacketReader): CameraRotationMessage {
        val angleY: Int = reader.getUnsigned(DataType.SHORT).toInt()
        val angleX: Int = reader.getUnsigned(DataType.SHORT).toInt()
        return CameraRotationMessage(angleX, angleY)
    }
}