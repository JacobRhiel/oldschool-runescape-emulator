package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rs.emulator.applyEach
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.actor.player.hasRequirementsFor
import rs.emulator.entity.material.containers.equipment
import rs.emulator.entity.material.containers.events.impl.AddContainerEvent
import rs.emulator.entity.material.containers.events.impl.RemoveContainerEvent
import rs.emulator.entity.material.containers.invalidateState
import rs.emulator.entity.material.containers.inventory
import rs.emulator.entity.material.containers.toEquipment
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.ItemActionExtensionPoint
import rs.emulator.reactive.onEachInstance
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
                    .invalidateState()
                    .onEach {
                        if (player.username() == "hunter23912") {
                            player.messages().sendChatMessage("Event $it")
                        }
                        val meta = ItemMetaDefinitionFactory.provide(it.item.id)
                        if (!meta.equipable_by_player) {
                            it.ignored = true
                            return@onEach
                        }
                        player.skillManager.hasRequirementsFor(it.item as Wearable) { msg ->
                            player.messages().sendChatMessage(msg)
                            it.ignored = true
                        }
                    }
                    .toEquipment(player.equipment())
                    .invalidateState()
                    .filterIsInstance<AddContainerEvent<Wearable>>()
                    .onEach {
                        if(player.username() == "hunter23912") {
                            player.messages().sendChatMessage("Add to equip event $it")
                        }
                    }
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