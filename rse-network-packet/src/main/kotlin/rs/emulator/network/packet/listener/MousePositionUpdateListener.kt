package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MousePositionUpdateMessage

/**
 *
 * @author Chk
 */
class MousePositionUpdateListener : GamePacketListener<MousePositionUpdateMessage>
{

    override fun handle(channel: Channel, player: Player, message: MousePositionUpdateMessage)
    {

    }

}