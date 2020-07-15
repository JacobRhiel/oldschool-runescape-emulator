package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ExamineEntityMessage

/**
 *
 * @author Chk
 */
class ExamineEntityListener : GamePacketListener<ExamineEntityMessage>
{

    override fun handle(channel: Channel, player: Player, message: ExamineEntityMessage)
    {



    }

}