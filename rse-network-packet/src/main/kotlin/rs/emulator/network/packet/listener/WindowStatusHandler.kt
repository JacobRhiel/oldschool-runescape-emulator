package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.IfOpenOverlayMessage
import rs.emulator.network.packet.atest.WindowStatusMessage

/**
 *
 * @author Chk
 */
class WindowStatusHandler : GamePacketListener<WindowStatusMessage>
{

    override fun handle(channel: Channel, message: WindowStatusMessage)
    {

        //channel.write(IfOpenOverlayMessage(548))

    }

}