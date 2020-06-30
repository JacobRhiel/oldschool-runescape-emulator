package rs.emulator.entity.actor.player.messages

import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

interface IContainerMessages {

    fun sendItemContainerFull(interfaceId: Int, component: Int, containerKey: Int, vararg items: Item)
    fun sendItemContainerPartial(interfaceId: Int, component: Int, containerKey: Int, vararg item: Pair<Item, Int>)

}