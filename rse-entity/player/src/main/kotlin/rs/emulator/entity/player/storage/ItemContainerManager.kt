package rs.emulator.entity.player.storage

import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class ItemContainerManager : IItemContainerManager<ItemContainer<Item>> {

    val containers : MutableMap<Int, ItemContainer<Item>> = mutableMapOf()

    override fun register(key: Int, container: ItemContainer<Item>, block: ItemContainer<Item>.() -> Unit) {
        block(container)
        containers[key] = container
    }

    override fun <G : Item> container(key: Int): ItemContainer<G> {
        return containers[key]!! as ItemContainer<G>
    }


}