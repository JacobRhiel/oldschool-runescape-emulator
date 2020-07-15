package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.KeepAliveMessage

/**
 *
 * @author Chk
 */
class KeepAliveHandler : GamePacketListener<KeepAliveMessage>
{

    override fun handle(channel: Channel, player: Player, message: KeepAliveMessage)
    {


    }

}