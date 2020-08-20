package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import rs.emulator.cache.definition.definition
import rs.emulator.definitions.entity.obj.ObjMetaDataDefinition
import rs.emulator.definitions.factories.ItemDefinitionFactory
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.actor.player.hasRequirementsFor
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.containers.*
import rs.emulator.entity.material.containers.events.impl.RemoveContainerEvent
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.ItemActionFactory
import rs.emulator.reactive.launch
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
                    .onEach {

                        val def = ItemMetaDefinitionFactory.provide(it.item.id)

                        println("def: " + def.equipable_weapon)

                        if(!def.equipable_weapon)
                            return@onEach

                        val weapon = player.equipment().elements[0]

                        println(weapon)

                        if(weapon.mainSlot == EquipmentSlot.WEAPON)
                        {

                            println("here?")

                            val def = if(weapon.id == -1) null else ItemDefinitionFactory.provide(weapon.id)

                            player.messages().setWidgetText(593, 1, def?.name ?: "Unarmed")

                            val metaDef = if(weapon.id == -1) null else ItemMetaDefinitionFactory.provide(weapon.id)

                            if(metaDef != null)
                                player.messages().setWidgetText(593, 2, "Category: ${metaDef.weapon.weapon_type}")

                        }

                    }
                    .invalidateState()
                    .filterIsInstance<RemoveContainerEvent<Wearable>>()
                    .toContainer(player.inventory())
                    .launch()
            }

        }

        flowOf(*RSPluginManager.getExtensions<ItemActionFactory>().toTypedArray())
            .map { it.registerItemAction(
                message.item,
                message.option,
                message.componentHash shr 16,
                message.componentHash and 255
            ) }
            .onEach { it.handleItemAction(ItemProvider.provide(message.item), message.option) }
            .launchIn(get<ActorScope>())
    }
}