package rs.emulator.entity.material.containers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import rs.emulator.collections.Container
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.attributes.MaterialAttributes
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.items.Item

/**
 * [ItemContainer] - mutating this container returns a flow of given [ItemContainerEvent]s
 * The return flows of [add] and [remove] are cold flows
 * @author javatar
 */

abstract class ItemContainer<I : Item>(val elements: Array<I>) : Container<I, ItemContainerEvent<I>> {

    val nextSlot: Int
        get() = elements.indexOfFirst { it === ItemData.EMPTY }
    val attributes = MaterialAttributes()

    fun addItem(element: I) = add(element).launchIn(CoroutineScope(Dispatchers.Unconfined))
    fun removeItem(element: I) = remove(element).launchIn(CoroutineScope(Dispatchers.Unconfined))

    fun isFull(): Boolean = all { it !== ItemData.EMPTY }
    fun isEmpty(): Boolean = all { it === ItemData.EMPTY }

    override fun iterator(): Iterator<I> {
        return elements.iterator()
    }
}