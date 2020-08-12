package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ClientModDetectionMessage

/**
 *
 * @author Chk
 */
class ClientModDetectionListener : GamePacketListener<ClientModDetectionMessage>
{

    override fun handle(
        player: Player,
        message: ClientModDetectionMessage
    )
    {


    }

}