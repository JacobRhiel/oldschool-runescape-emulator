package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.KeyBoardEventMessage

/**
 *
 * @author Chk
 */
class KeyBoardEventListener : GamePacketListener<KeyBoardEventMessage>
{

    override fun handle(
        player: Player,
        message: KeyBoardEventMessage
    )
    {



    }

}