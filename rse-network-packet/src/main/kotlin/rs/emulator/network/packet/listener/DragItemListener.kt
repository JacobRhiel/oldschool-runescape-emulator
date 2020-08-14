package rs.emulator.network.packet.listener

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import rs.emulator.entity.material.provider.ItemProvider
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.message.incoming.DragItemMessage
import rs.emulator.plugins.RSPluginManager
import rs.emulator.plugins.extensions.factories.widgets.DraggedItemFactory
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class DragItemListener : GamePacketListener<DragItemMessage> {
    override fun handle(
        player: Player,
        message: DragItemMessage
    ) {

        flowOf(*RSPluginManager.getExtensions<DraggedItemFactory>().toTypedArray())
            .map { it.registerDraggedItemAction(
                message.draggedItemId,
                message.draggedHash,
                message.draggedChildIndex,
                message.clickedItemId,
                message.clickedHash,
                message.clickedChildIndex
            ) }
            .onEach { it.handleDraggedItem(
                player,
                ItemProvider.provide(message.draggedItemId),
                message.draggedHash shr 16,
                message.draggedHash and 255,
                message.draggedChildIndex,
                ItemProvider.provide(message.clickedItemId),
                message.clickedHash shr 16,
                message.clickedHash and 255,
                message.clickedChildIndex
            ) }
            .launchIn(get<ActorScope>())

    }
}