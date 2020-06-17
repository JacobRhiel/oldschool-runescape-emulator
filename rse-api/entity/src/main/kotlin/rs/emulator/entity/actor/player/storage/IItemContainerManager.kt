package rs.emulator.entity.actor.player.storage

import rs.emulator.entity.actor.player.storage.container.ItemContainer

/**
 *
 * @author javatar
 */

interface IItemContainerManager<T : ItemContainer<*>> {

    fun register(key : Int, container : T, block : T.() -> Unit = {})

    fun container(key : Int) : T

}