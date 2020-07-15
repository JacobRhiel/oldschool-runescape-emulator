package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.applyEach
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.actor.player.storage.equipment
import rs.emulator.entity.material.factories.WearableItemFactory
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.ObjActionMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.ItemActionExtensionPoint

/**
 *
 * @author Chk
 */
class ObjActionListener : GamePacketListener<ObjActionMessage> {
    override fun handle(channel: Channel, player: Player, message: ObjActionMessage) {

        println("[ObjAction] - ${message.item}, ${message.option}, ${message.componentHash}")


        when (message.opcode) {

            74 -> {
                val item = WearableItemFactory.createFromMetaData(message.item, 1)
                val meta = ItemMetaDefinitionFactory.provide(item.id)
                if (meta.equipable_by_player) {
                    if (meta.equipment != null) {
                        val e = meta.equipment!!
                        val reqs = e.requirements
                        //TODO - check equipment level requirements
                    }
                    player.containerManager().equipment().add(item)
                }
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