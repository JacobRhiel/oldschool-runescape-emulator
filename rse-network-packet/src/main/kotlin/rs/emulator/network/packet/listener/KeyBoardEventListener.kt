package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.KeyBoardEventMessage

/**
 *
 * @author Chk
 */
class KeyBoardEventListener : GamePacketListener<KeyBoardEventMessage>
{

    override fun handle(channel: Channel, player: Player, message: KeyBoardEventMessage)
    {



    }

}