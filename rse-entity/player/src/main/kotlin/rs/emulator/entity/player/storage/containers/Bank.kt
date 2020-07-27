package rs.emulator.entity.player.storage.containers

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

class Bank : ItemContainer<Item>(Array(800) { ItemData.EMPTY }, ItemData.EMPTY) {

    override fun nextSlot(): Int {
        TODO("Not yet implemented")
    }

    override fun add(element: Item) {
        TODO("Not yet implemented")
    }

    override fun remove(element: Item) {
        TODO("Not yet implemented")
    }
}