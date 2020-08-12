package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MouseIdleTickMessage

/**
 *
 * @author Chk
 */
class MouseIdleTickListener : GamePacketListener<MouseIdleTickMessage>
{

    override fun handle(
        player: Player,
        message: MouseIdleTickMessage
    )
    {



    }

}