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

    override fun handle(channel: Channel, message: ConsoleCommandMessage)
    {

        println("console command: ${message.args}")

        val session = channel.attr(SESSION_KEY).get() as PacketSession
        val player : Player = channel.attr(session.PLAYER_KEY).get()

        val commandExtensions = RSPluginManager.getExtensions(CommandExtensionPoint::class.java)

        commandExtensions.forEach {
            if(it.hasRights(player)) {
                it.execute(player, message.args)
            }
        }

    }

}