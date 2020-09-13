package rs.emulator.entity.material.containers.impl.bank

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
class BankTab(val tabId: Int) : ItemContainer<Item>(95, arrayOf()) {

    private val items: MutableList<Item> = mutableListOf()

    override val elements: Array<Item>
        get() = items.toTypedArray()

    val size: Int get() = if(elements.isEmpty()) 0 else elements.filterNot { it === ItemData.EMPTY }.size

    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        val items = this@BankTab.items
        val index = items.indexOf(element)
        if(index != -1) {
            val i = items[index].copy()
            val event = StackedContainerEvent(slot = index, item = i)
            emit(event)
            if(!event.ignored) {
                i += element
                this@BankTab.items[index] = i
            }
        } else {
            val event = AddContainerEvent(index, element)
            emit(event)

            if(!event.ignored) {
                items.add(element)
            }
        }

    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        val items = this@BankTab.items
        val index = items.indexOf(element)
        if(index != -1) {
            val item = items[index]
            if (element.amount < item.amount) {
                val event = StackedContainerEvent(true, index, element.copy())
                if(!event.ignored) {
                    item -= element
                    this@BankTab.items[index] = item
                }
            } else {
                val event = RemoveContainerEvent(item.copy(), index)
                emit(event)
                if(!event.ignored) {
                    items.removeAt(index)
                }
            }
        } else emit(NoSuchItemContainerEvent(element))
    }

    fun replace(element: Item, replace: Item) = flow {
        val items = this@BankTab.items
        val index = items.indexOf(element)
        if(index != -1) {
            val event = ReplaceItemContainerEvent(replace, element, index)
            emit(event)
            this@BankTab.items[index] = replace
        }
    }

}