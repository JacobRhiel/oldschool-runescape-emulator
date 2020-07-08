package rs.emulator.plugin.commands

import com.xenomachina.argparser.ArgParser
import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.inventory
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.plugin.commands.impl.SpawnItem
import rs.emulator.plugins.extensions.CommandExtensionPoint

/**
 *
 * @author javatar
 */
@Extension(plugins = ["COMMAND_PLUGIN"])
class CommandExtension : CommandExtensionPoint {

    override fun execute(player: IPlayer, command: String) {
        val name = command.split(" ")[0]
        val args = command.substringAfter(name).trim().split(" ")

        when(name) {
            "item" -> {
                val item = ArgParser(args.toTypedArray()).parseInto(::SpawnItem)
                val spawned : Item = ItemProvider.provide(item.id, item.amt)
                player.inventory().add(spawned)
            }
        }

    }

    override fun hasRights(player : IPlayer): Boolean {
        return true
    }
}