package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.KeepAliveMessage

/**
 *
 * @author Chk
 */
class KeepAliveHandler : GamePacketListener<KeepAliveMessage>
{

    override fun handle(channel: Channel, message: KeepAliveMessage)
    {


    }

}