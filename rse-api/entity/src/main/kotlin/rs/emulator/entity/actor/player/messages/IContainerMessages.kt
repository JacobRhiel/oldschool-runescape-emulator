package rs.emulator.entity.actor.player.messages

import rs.emulator.entity.material.containers.ItemContainer

/**
 *
 * @author javatar
 */

interface IContainerMessages : IMessages {

    fun sendItemContainerFull(interfaceId: Int, component: Int, containerKey: Int, container: ItemContainer<*>)
    fun sendItemContainerPartial(interfaceId: Int, component: Int, containerKey: Int, container: ItemContainer<*>)

}