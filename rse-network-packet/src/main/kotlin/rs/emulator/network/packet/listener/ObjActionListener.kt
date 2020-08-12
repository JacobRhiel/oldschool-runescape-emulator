package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rs.emulator.applyEach
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.actor.player.hasRequirementsFor
import rs.emulator.entity.material.containers.*
import rs.emulator.entity.material.containers.events.impl.AddContainerEvent
import rs.emulator.entity.material.containers.events.impl.RemoveContainerEvent
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.ItemActionExtensionPoint
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
class ObjActionListener : GamePacketListener<ObjActionMessage> {
    override fun handle(
        player: Player,
        message: ObjActionMessage
    ) {
        println("[ObjAction] - ${message.item}, ${message.option}, ${message.componentHash}")
        when (message.opcode) {
            7 -> {
                player.inventory().remove(ItemProvider.provide(message.item))
                    .invalidateState()
                    .onEachEvent<RemoveContainerEvent<Item>> {
                        val meta = ItemMetaDefinitionFactory.provide(it.item.id)
                        if (!meta.equipable_by_player) {
                            it.ignored = true
                            return@onEachEvent
                        }
                        player.skillManager.hasRequirementsFor(it.item as Wearable) { msg ->
                            player.messages().sendChatMessage(msg)
                            it.ignored = true
                        }
                        player.syncInfo.addMaskFlag(PlayerUpdateFlag.APPEARANCE)
                    }
                    .onEach {
                        if(player.username() == "hunter23912" || player.username() == "chk") {
                            player.messages().sendChatMessage("Event $it")
                        }
                    }
                    .filterIsInstance<RemoveContainerEvent<Item>>()
                    .toEquipment(player.equipment())
                    .invalidateState()
                    .filterIsInstance<RemoveContainerEvent<Wearable>>()
                    .toContainer(player.inventory())
                    .launchIn(get<ActorScope>())
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