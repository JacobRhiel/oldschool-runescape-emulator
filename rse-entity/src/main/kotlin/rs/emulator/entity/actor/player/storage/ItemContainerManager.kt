package rs.emulator.entity.actor.player.storage

import rs.emulator.entity.material.Item

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

    override fun container(key: Int): ItemContainer<Item> = containers[key]!!


}