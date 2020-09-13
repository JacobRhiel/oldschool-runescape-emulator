package rs.emulator.plugin.commands

import com.xenomachina.argparser.ArgParser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.pf4j.Extension
import rs.emulator.entity.actor.combat.prayer.Prayers
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.actor.player.messages.chat.ChatMessageType
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.plugin.commands.impl.MessageGenerator
import rs.emulator.plugin.commands.impl.SetSkillAttribute
import rs.emulator.plugin.commands.impl.SpawnItem
import rs.emulator.plugins.extensions.factories.CommandFactory
import rs.emulator.skills.math.ExperienceMath

/**
 *
 * @author javatar
 */
@ExperimentalCoroutinesApi
@Extension(plugins = ["COMMAND_PLUGIN"])
class CommandExtension : CommandFactory {

    override fun execute(player: IPlayer, command: String) {
        val name = command.split(" ")[0]
        val args = command.substringAfter(name).trim().split(" ")

        println("$name - $args")

        when (name) {
            "item" -> {
                println("Spawning item")
                val item = ArgParser(args.toTypedArray()).parseInto(::SpawnItem)
                val spawned: Item = ItemProvider.provide(item.id, item.amt)
                player.inventory().addItem(spawned)
            }
            "coins" -> {
                val coins = ItemProvider.provide<StandardItem>(995, 2147000000)
                player.inventory().addItem(coins)
            }
            "testAnim" -> {
                val animation = 3864

            }
            "set-skill" -> {
                val skill = ArgParser(args.toTypedArray()).parseInto(::SetSkillAttribute)
                if(skill.lvl <= 99) {
                    player.skillManager.skills[skill.id].staticLevel = skill.lvl
                    player.skillManager.skills[skill.id].level = skill.lvl
                    player.skillManager.skills[skill.id].experience = ExperienceMath.xpForLevel(skill.lvl)
                } else {
                    val lvl = ExperienceMath.levelForXp(skill.lvl.toDouble())
                    player.skillManager.skills[skill.id].staticLevel = lvl
                    player.skillManager.skills[skill.id].level = lvl
                    player.skillManager.skills[skill.id].experience = skill.lvl
                }
                player.skillManager.skillState.value = listOf(player.skillManager.skills[skill.id])
            }
            "msg" -> {
                val msg = ArgParser(args.toTypedArray()).parseInto(::MessageGenerator)
                player.messages().sendChatMessage(msg.msg, ChatMessageType.fromType(msg.type))
            }
            "prayer" -> {
                player.combatFactory.prayerManager.togglePrayer(Prayers.PROTECT_FROM_MELEE)
            }
            "varbit" -> {
                val large = args[0].equals("large", true) || args[0].equals("l", true)
                val varbit = args[1].toInt()
                if(!large)
                    player.messages().sendLargeVarp(varbit, args[2].toInt())
                else
                    player.messages().sendSmallVarp(varbit, args[2].toInt())

                player.messages().sendChatMessage("Sending ${if(large) "large" else "small" } varbit: $varbit with value: ${args[1].toInt()}.")
            }
            "inv" -> {
            }
        }

    }

    override fun hasRights(player: IPlayer): Boolean {
        return true
    }
}