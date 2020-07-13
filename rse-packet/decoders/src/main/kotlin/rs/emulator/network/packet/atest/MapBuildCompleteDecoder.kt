package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MapBuildCompleteDecoder : PacketDecoder<MapBuildCompleteMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): MapBuildCompleteMessage {

        return MapBuildCompleteMessage()

    }

}