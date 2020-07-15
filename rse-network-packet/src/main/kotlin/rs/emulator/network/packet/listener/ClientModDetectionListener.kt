package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ClientModDetectionMessage

/**
 *
 * @author Chk
 */
class ClientModDetectionListener : GamePacketListener<ClientModDetectionMessage>
{

    override fun handle(channel: Channel, player: Player, message: ClientModDetectionMessage)
    {


    }

}