package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MousePositionUpdateMessage

/**
 *
 * @author Chk
 */
class MousePositionUpdateListener : GamePacketListener<MousePositionUpdateMessage>
{

    override fun handle(
        player: Player,
        message: MousePositionUpdateMessage
    )
    {

    }

}