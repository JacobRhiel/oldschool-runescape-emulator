package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.AppletFocusEventMessage

/**
 *
 * @author Chk
 */
class AppletFocusEventListener : GamePacketListener<AppletFocusEventMessage>
{

    override fun handle(channel: Channel, player: Player, message: AppletFocusEventMessage)
    {



    }

}