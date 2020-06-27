package rs.emulator.entity.actor.player.storage

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface IItemContainerManager<T : ItemContainer<*>> {

    fun register(key : Int, container : T, block : T.() -> Unit = {})

    fun <G : Item> container(key : Int) : ItemContainer<G>

}