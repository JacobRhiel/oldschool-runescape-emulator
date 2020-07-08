package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.packet.atest.ConsoleCommandMessage
import rs.emulator.network.packet.session.PacketSession
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.CommandExtensionPoint

/**
 *
 * @author Chk
 */
class ConsoleCommandListener : GamePacketListener<ConsoleCommandMessage>
{

    override fun handle(channel: Channel, player: Player, message: ConsoleCommandMessage)
    {

        val commandExtensions = RSPluginManager.getExtensions(CommandExtensionPoint::class.java)

        println("running against ${commandExtensions.size} command plugins.")

        commandExtensions.forEach {
            if(it.hasRights(player)) {
                it.execute(player, message.args)
            }
        }

    }

}