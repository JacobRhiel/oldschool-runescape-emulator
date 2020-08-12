package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.AppletFocusEventMessage

/**
 *
 * @author Chk
 */
class AppletFocusEventListener : GamePacketListener<AppletFocusEventMessage>
{

    override fun handle(
        player: Player,
        message: AppletFocusEventMessage
    )
    {



    }

}