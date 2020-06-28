package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.ConsoleCommandMessage

/**
 *
 * @author Chk
 */
class ConsoleCommandListener : GamePacketListener<ConsoleCommandMessage>
{

    override fun handle(channel: Channel, player: Player, message: ConsoleCommandMessage)
    {

        println("console command: ${message.args}")

    }

}