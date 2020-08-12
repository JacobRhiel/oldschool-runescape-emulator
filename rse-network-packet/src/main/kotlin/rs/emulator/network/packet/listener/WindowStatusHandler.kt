package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.WindowStatusMessage

/**
 *
 * @author Chk
 */
class WindowStatusHandler : GamePacketListener<WindowStatusMessage>
{

    override fun handle(
        player: Player,
        message: WindowStatusMessage
    )
    {

        //channel.write(IfOpenOverlayMessage(548))

    }

}