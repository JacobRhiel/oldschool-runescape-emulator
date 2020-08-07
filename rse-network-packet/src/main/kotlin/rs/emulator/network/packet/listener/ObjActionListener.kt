package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rs.emulator.applyEach
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.material.containers.equipment
import rs.emulator.entity.material.containers.invalidateState
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.material.containers.toEquipment
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.ItemActionExtensionPoint
import rs.emulator.skills.Skills
import rs.emulator.skills.wieldRequirementMessage

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
class ObjActionListener : GamePacketListener<ObjActionMessage> {
    override fun handle(channel: Channel, player: Player, message: ObjActionMessage) {

        println("[ObjAction] - ${message.item}, ${message.option}, ${message.componentHash}")


        when (message.opcode) {

            7 -> {
                player.inventory().remove(ItemProvider.provide(message.item))
                    .onEach {
                        val meta = ItemMetaDefinitionFactory.provide(it.item.id)
                        if (!meta.equipable_by_player) {
                            it.ignored = true
                            return@onEach
                        }
                        val e = meta.equipment
                        val reqs = e.requirements
                        val skills = player.skillAttributes
                        if (!skills.hasActualLevel(reqs.attack, Skills.ATTACK)) {
                            player.messages()
                                .sendChatMessage(Skills.ATTACK.wieldRequirementMessage(reqs.attack, meta.wiki_name))
                            it.ignored = true
                        }
                        if (!skills.hasActualLevel(reqs.defence, Skills.DEFENCE)) {
                            player.messages()
                                .sendChatMessage(Skills.DEFENCE.wieldRequirementMessage(reqs.defence, meta.wiki_name))
                            it.ignored = true
                        }
                        if (!skills.hasActualLevel(reqs.magic, Skills.MAGIC)) {
                            player.messages()
                                .sendChatMessage(Skills.MAGIC.wieldRequirementMessage(reqs.magic, meta.wiki_name))
                            it.ignored = true
                        }
                        if (!skills.hasActualLevel(reqs.range, Skills.RANGED)) {
                            player.messages()
                                .sendChatMessage(Skills.RANGED.wieldRequirementMessage(reqs.range, meta.wiki_name))
                            it.ignored = true
                        }
                        if (!skills.hasActualLevel(reqs.strength, Skills.STRENGTH)) {
                            player.messages()
                                .sendChatMessage(Skills.STRENGTH.wieldRequirementMessage(reqs.strength, meta.wiki_name))
                            it.ignored = true
                        }
                    }
                    .invalidateState()
                    .toEquipment(player.equipment())
                    .invalidateState()
                    .launchIn(CoroutineScope(Dispatchers.Default))
            }

        }



        RSPluginManager.getExtensions(ItemActionExtensionPoint::class.java).applyEach {
            player.interact(
                message.item,
                message.option,
                message.componentHash shr 16,
                message.componentHash and 255
            )
        }
    }
}