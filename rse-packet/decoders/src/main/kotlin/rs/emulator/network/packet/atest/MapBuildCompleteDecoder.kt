package rs.emulator.network.packet.atest

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class MapBuildCompleteDecoder : PacketDecoder<MapBuildCompleteMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): MapBuildCompleteMessage
    {

        return MapBuildCompleteMessage()

    }

}