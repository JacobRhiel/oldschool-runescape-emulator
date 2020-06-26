package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.atest.KeyBoardEventMessage

/**
 *
 * @author Chk
 */
class KeyBoardEventListener : GamePacketListener<KeyBoardEventMessage>
{

    override fun handle(channel: Channel, message: KeyBoardEventMessage)
    {



    }

}