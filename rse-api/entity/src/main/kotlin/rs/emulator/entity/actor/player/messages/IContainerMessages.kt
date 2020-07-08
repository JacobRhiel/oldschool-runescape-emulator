package rs.emulator.entity.actor.player.messages

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface IContainerMessages {

    fun sendItemContainerFull(interfaceId: Int, component: Int, containerKey: Int, container: ItemContainer<*>)
    fun sendItemContainerPartial(interfaceId: Int, component: Int, containerKey: Int, container : ItemContainer<*>)

}