package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.MouseClickMessage

/**
 *
 * @author Chk
 */
class MouseClickListener : GamePacketListener<MouseClickMessage>
{

    override fun handle(channel: Channel, player: Player, message: MouseClickMessage)
    {



    }

}