package rs.emulator.entity.material.containers.impl.bank

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.emulator.collections.Container
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class BankTab(val bank: Bank) : Container<Item, ItemContainerEvent<Item>> {

    val elements = mutableListOf<Item>()

    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {

    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> = flow {

    }

    fun nextSlot(): Int {
        return 0
    }

    override fun iterator(): Iterator<Item> {
        return elements.iterator()
    }
}