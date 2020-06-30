package rs.emulator.entity.actor.player.storage

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface IItemContainerManager {

    fun <I : Item, C : ItemContainer<I>> register(key : Int, container : C, block : C.() -> Unit)

    fun <G : Item> container(key : Int) : ItemContainer<G>

}