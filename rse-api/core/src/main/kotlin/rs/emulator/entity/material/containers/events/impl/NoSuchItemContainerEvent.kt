package rs.emulator.entity.material.containers.events.impl

import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.Slot
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

data class NoSuchItemContainerEvent<I : Item>(
    override val item: I,
    override var slot: Slot = -1,
    override var ignored: Boolean = false
) : ItemContainerEvent<I>