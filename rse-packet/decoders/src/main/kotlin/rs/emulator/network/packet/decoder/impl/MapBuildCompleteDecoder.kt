package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.MapBuildCompleteMessage

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