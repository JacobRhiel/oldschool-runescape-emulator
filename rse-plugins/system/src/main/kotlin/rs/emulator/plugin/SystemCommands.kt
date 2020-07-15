package rs.emulator.plugin

import com.xenomachina.argparser.ArgParser
import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.CommandFactory

/**
 * Its probably not a good idea to disable or stop this plugin, otherwise this will require
 * a server restart to re-enable
 * @author javatar
 */

@Extension(plugins = ["SYSTEM_PLUGIN"])
class SystemCommands : CommandFactory {
    override fun execute(player: IPlayer, command: String) {
        val name = command.split(" ")[0]
        val cmd = command.substringAfter(name).trim()
        when (name) {
            "pstop" -> {
                ArgParser(cmd.split(" ").toTypedArray()).parseInto(::PluginInfo).run {
                    RSPluginManager.stopPlugin(this.pluginId)
                }
            }
            "splugin" -> {
                ArgParser(cmd.split(" ").toTypedArray()).parseInto(::PluginInfo).run {
                    RSPluginManager.startPlugin(this.pluginId)
                }
            }
            "rplugin" -> {
                ArgParser(cmd.split(" ").toTypedArray()).parseInto(::PluginInfo).run {
                    val wrapper = RSPluginManager.getPlugin(this.pluginId)
                    RSPluginManager.stopPlugin(this.pluginId)
                    RSPluginManager.unloadPlugin(this.pluginId)
                    RSPluginManager.loadPlugin(wrapper.pluginPath)
                    RSPluginManager.startPlugin(this.pluginId)
                }
            }
            "lplugins" -> {
                RSPluginManager.plugins.forEach {
                    println(it.pluginId)
                }
            }
        }
    }

    override fun hasRights(player: IPlayer): Boolean {
        return player.username() == "Javatar" || player.username() == "Chk"
    }
}