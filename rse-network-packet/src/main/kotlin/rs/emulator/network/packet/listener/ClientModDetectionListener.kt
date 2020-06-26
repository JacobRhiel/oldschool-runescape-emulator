package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.ClientModDetectionMessage

/**
 *
 * @author Chk
 */
class ClientModDetectionListener : GamePacketListener<ClientModDetectionMessage>
{

    override fun handle(channel: Channel, message: ClientModDetectionMessage)
    {


    }

}