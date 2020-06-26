package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.AppletFocusEventMessage

/**
 *
 * @author Chk
 */
class AppletFocusEventListener : GamePacketListener<AppletFocusEventMessage>
{

    override fun handle(channel: Channel, message: AppletFocusEventMessage)
    {



    }

}