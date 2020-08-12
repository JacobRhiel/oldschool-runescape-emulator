package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MapBuildCompleteMessage

/**
 *
 * @author Chk
 */
class MapBuildCompleteHandler : GamePacketListener<MapBuildCompleteMessage>
{

    override fun handle(
        player: Player,
        message: MapBuildCompleteMessage
    )
    {


    }

}