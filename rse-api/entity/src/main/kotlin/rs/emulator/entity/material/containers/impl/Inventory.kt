package rs.emulator.entity.material.containers.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.*
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class Inventory : ItemContainer<Item>(
    93,
    Array(28) { ItemData.EMPTY }
) {
    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        var dirty = false
        if (isFull()) {
            emit(FullContainerEvent(element))
            return@flow
        }
        when {
            element.stackable -> {
                var slot = indexOf(element)
                if (slot != -1) {
                    val found = elements[slot].copy()
                    found += element
                    val event = StackedContainerEvent(true, slot, found)
                    emit(event)
                    if (!event.ignored) {
                        elements[slot] = found
                        dirty = true
                    }
                } else {
                    slot = nextSlot
                    val item = element.copy()
                    val event = StackedContainerEvent(false, slot, item)
                    emit(event)
                    if (!event.ignored) {
                        elements[event.slot] = item
                        dirty = true
                    }
                }
            }
            element.amount > 1 -> {
                repeat(element.amount) {
                    if (isFull()) {
                        emit(FullContainerEvent(element.copy(element.amount - it)))
                        return@repeat
                    }
                    emitAll(add(element.copy(1)))
                }
            }
            else -> {
                val slot = nextSlot
                val item = element.copy()
                val event = AddContainerEvent(slot, item)
                emit(event)
                if (!event.ignored) {
                    elements[event.slot] = item
                    dirty = true
                }
            }
        }
        if (dirty) {
            emit(UpdateContainerStateEvent(this@Inventory, ItemData.EMPTY))
        }
    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        var dirty = false
        if (isEmpty()) {
            emit(EmptyContainerEvent(element))
            return@flow
        }
        if (element === ItemData.EMPTY) {
            emit(NoSuchItemContainerEvent(element))
            return@flow
        }
        val slot = indexOf(element)
        if (slot != -1) {
            when {
                element.stackable -> {
                    val found = elements[slot].copy()
                    found -= element
                    val event = StackedContainerEvent(slot = slot, item = found)
                    emit(event)
                    if (!event.ignored) {
                        elements[slot] = if (found.amount <= 0) {
                            ItemData.EMPTY
                        } else {
                            found
                        }
                        dirty = true
                    }
                }
                element.amount > 1 -> {
                    repeat(element.amount) {
                        emitAll(remove(element.copy(1)))
                    }
                }
                else -> {
                    val item = element.copy()
                    val event = RemoveContainerEvent(item, slot)
                    emit(event)
                    if (!event.ignored) {
                        elements[event.slot] = ItemData.EMPTY
                        dirty = true
                    }
                }
            }
        } else {
            emit(NoSuchItemContainerEvent(element))
        }
        if (dirty) {
            emit(UpdateContainerStateEvent(this@Inventory, ItemData.EMPTY))
        }
    }
}