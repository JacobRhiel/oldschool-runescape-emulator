package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.MouseIdleTickMessage

/**
 *
 * @author Chk
 */
class MouseIdleTickListener : GamePacketListener<MouseIdleTickMessage>
{

    override fun handle(channel: Channel, player: Player, message: MouseIdleTickMessage)
    {



    }

}