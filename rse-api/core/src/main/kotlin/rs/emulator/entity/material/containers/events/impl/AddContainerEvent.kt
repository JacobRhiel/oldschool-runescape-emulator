package rs.emulator.entity.material.containers.events.impl

import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.Slot
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

data class AddContainerEvent<I : Item>(
    override var slot: Slot,
    override val item: I,
    override var ignored: Boolean = false
) : ItemContainerEvent<I>