package rs.emulator.plugin.commands

import com.xenomachina.argparser.ArgParser
import org.pf4j.Extension
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.storage.inventory
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.plugin.commands.impl.SetSkillAttribute
import rs.emulator.plugin.commands.impl.SpawnItem
import rs.emulator.plugins.extensions.factories.CommandFactory
import rs.emulator.skills.ExperienceEvent
import rs.emulator.skills.LevelEvent

/**
 *
 * @author javatar
 */
@Extension(plugins = ["COMMAND_PLUGIN"])
class CommandExtension : CommandFactory {

    override fun execute(player: IPlayer, command: String) {
        val name = command.split(" ")[0]
        val args = command.substringAfter(name).trim().split(" ")

        when (name) {
            "item" -> {
                val item = ArgParser(args.toTypedArray()).parseInto(::SpawnItem)
                val spawned: Item = ItemProvider.provide(item.id, item.amt)
                player.inventory().add(spawned)
            }
            "coins" -> {
                val coins = ItemProvider.provide<StandardItem>(995, 2147000000)
                player.inventory().add(coins)
            }
            "testAnim" -> {
                val animation = 3864

            }
            "set-skill" -> {
                val skill = ArgParser(args.toTypedArray()).parseInto(::SetSkillAttribute)
                player.skillAttributes.setAttribute(
                    if (skill.exp != 0) ExperienceEvent(skill.id, skill.exp, skill.exp)
                    else null,
                    if (skill.lvl != 0) LevelEvent(skill.id, skill.lvl, skill.lvl)
                    else null,
                    skill.show
                )
            }
        }

    }

    override fun hasRights(player: IPlayer): Boolean {
        return true
    }
}