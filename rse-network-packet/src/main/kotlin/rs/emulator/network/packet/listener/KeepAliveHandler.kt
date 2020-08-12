package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.KeepAliveMessage

/**
 *
 * @author Chk
 */
class KeepAliveHandler : GamePacketListener<KeepAliveMessage>
{

    override fun handle(
        player: Player,
        message: KeepAliveMessage
    )
    {


    }

}