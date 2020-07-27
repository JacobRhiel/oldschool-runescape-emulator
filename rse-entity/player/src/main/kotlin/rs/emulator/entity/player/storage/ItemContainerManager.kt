package rs.emulator.entity.player.storage

import rs.emulator.entity.actor.player.storage.IItemContainerManager
import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class ItemContainerManager : IItemContainerManager {

    val containers: MutableMap<Int, ItemContainer<*>> = mutableMapOf()


    override fun <I : Item, C : ItemContainer<I>> register(key: Int, container: C, block: C.() -> Unit) {
        block(container)
        containers[key] = container
    }

    override fun <G : Item> container(key: Int): ItemContainer<G>? {
        return containers[key] as ItemContainer<G>
    }

}