package rs.emulator.entity.material.containers.events.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.Slot
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class UpdateContainerStateEvent(
    val container: ItemContainer<*>,
    override val item: Item,
    override var slot: Slot = -1,
    override var ignored: Boolean = false
) : ItemContainerEvent<Item> {

    override fun toString(): String {
        return "UpdateContainerStateEvent(container=${container.key}, item=$item, slot=$slot, ignored=$ignored)"
    }
}