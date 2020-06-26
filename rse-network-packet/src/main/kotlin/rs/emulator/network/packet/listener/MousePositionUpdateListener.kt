package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.MousePositionUpdateMessage

/**
 *
 * @author Chk
 */
class MousePositionUpdateListener : GamePacketListener<MousePositionUpdateMessage>
{

    override fun handle(channel: Channel, message: MousePositionUpdateMessage)
    {

    }

}