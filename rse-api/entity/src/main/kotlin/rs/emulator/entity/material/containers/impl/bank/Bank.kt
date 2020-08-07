package rs.emulator.entity.material.containers.impl.bank

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.emulator.Repository
import rs.emulator.collections.Container
import rs.emulator.definitions.widget.InvDefinition
import rs.emulator.entity.material.attributes.MaterialAttributes
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class Bank : Container<Item, ItemContainerEvent<Item>> {

    val items = mutableMapOf<Int, BankTab>().also { it[0] = BankTab(this) }
    val settings = MaterialAttributes()
    val size: Int
        get() {
            val total = items.values.sumBy { it.elements.size }
            val def = Repository.getDefinition<InvDefinition>(95)
            return def.size - total
        }
    val nextSlot: Int
        get() {

            return 0
        }

    init {
        settings["CURRENT_BANK_TAB"] = 0
    }

    override fun add(element: Item): Flow<ItemContainerEvent<Item>> = flow {
        val currentTab: Int = settings["CURRENT_BANK_TAB"]
        val bankTab = items.getOrDefault(currentTab, items[0]!!)


    }

    override fun remove(element: Item): Flow<ItemContainerEvent<Item>> = flow {

    }

    override fun iterator(): Iterator<Item> {
        val currentTab: Int = settings["CURRENT_BANK_TAB"]
        return items[currentTab]?.elements?.iterator() ?: emptyList<Item>().iterator()
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