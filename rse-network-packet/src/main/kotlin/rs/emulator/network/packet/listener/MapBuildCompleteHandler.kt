package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MapBuildCompleteMessage

/**
 *
 * @author Chk
 */
class MapBuildCompleteHandler : GamePacketListener<MapBuildCompleteMessage>
{

    override fun handle(channel: Channel, player: Player, message: MapBuildCompleteMessage)
    {


    }

}