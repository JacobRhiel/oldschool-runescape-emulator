package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.IfOpenOverlayMessage
import rs.emulator.network.packet.atest.WindowStatusMessage

/**
 *
 * @author Chk
 */
class WindowStatusHandler : GamePacketListener<WindowStatusMessage>
{

    override fun handle(channel: Channel, player: Player, message: WindowStatusMessage)
    {

        //channel.write(IfOpenOverlayMessage(548))

    }

}