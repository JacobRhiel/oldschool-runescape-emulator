package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ConsoleCommandMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CommandFactory

/**
 *
 * @author Chk
 */
class ConsoleCommandListener : GamePacketListener<ConsoleCommandMessage>
{

    override fun handle(channel: Channel, player: Player, message: ConsoleCommandMessage)
    {

        val commandExtensions = RSPluginManager.getExtensions(CommandFactory::class.java)

        println("running against ${commandExtensions.size} command plugins.")

        commandExtensions.forEach {
            if(it.hasRights(player)) {
                it.execute(player, message.args)
            }
        }

    }

}