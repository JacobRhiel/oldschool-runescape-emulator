package rs.emulator.plugins.extensions.factories.widgets.actions

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface DraggedItemAction {

    fun handleDraggedItem(
        player: IPlayer,
        draggedItem: Item,
        draggedParentId: Int,
        draggedChildId: Int,
        draggedChildIndex: Int,
        clickedItem: Item,
        clickedParentId: Int,
        clickedChildId: Int,
        clickedChildIndex: Int
    )

}