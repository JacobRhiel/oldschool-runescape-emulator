package rs.emulator.entity.material.containers.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.AddContainerEvent
import rs.emulator.entity.material.containers.events.impl.FullContainerEvent
import rs.emulator.entity.material.containers.events.impl.StackedContainerEvent
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class Bank : ItemContainer<Item>(Array(800) { ItemData.EMPTY }) {

    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        if (isFull()) {
            emit(FullContainerEvent(element))
            return@flow
        }
        val e = element.toUnnoted()
        val slot = indexOf(e)
        if (slot != -1) {
            if (elements[slot].amount == Integer.MAX_VALUE) {
                emit(FullContainerEvent(elements[slot], slot))
                return@flow
            }
            val found = elements[slot].copy()
            found += e
            val remaining = found.amount - elements[slot].amount
            val event = StackedContainerEvent(true, slot, found.copy(remaining))
            emit(event)
            if (!event.ignored) {
                elements[slot] = found
            }
        } else {
            val newSlot = nextSlot
            val item = e.copy()
            val event = AddContainerEvent(newSlot, item)
            emit(event)
            if (!event.ignored) {
                elements[event.slot] = event.item
            }
        }
    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> {
        TODO("Not yet implemented")
    }

    internal class ItemPlaceHolder(id: Int) : Item(id) {
        override fun copy(amount: Int, stackable: Boolean): ItemPlaceHolder {
            return ItemPlaceHolder(id).also { it.attributes.setAttributes(this.attributes.map) }
        }

        override fun toNoted(): ItemPlaceHolder {
            return this
        }

        override fun toUnnoted(): ItemPlaceHolder {
            return this
        }
    }

}