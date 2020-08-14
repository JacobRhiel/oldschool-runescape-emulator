package rs.emulator.plugins.extensions.factories.widgets

import org.pf4j.ExtensionPoint
import rs.emulator.plugins.extensions.factories.widgets.actions.DraggedItemAction

/**
 *
 * @author javatar
 */

interface DraggedItemFactory : ExtensionPoint {

    fun registerDraggedItemAction(
        draggedItemId: Int,
        draggedHash: Int,
        draggedChildIndex: Int,
        clickedItemId: Int,
        clickedHash: Int,
        clickedChildIndex: Int
    ) : DraggedItemAction

}