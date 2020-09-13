package rs.emulator.entity.material.containers.events.impl

import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.Slot
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

data class SwapSlotsContainerEvent(override val item: Item, val fromItem : Item, override var slot: Slot, val toSlot : Slot, override var ignored: Boolean = false) : ItemContainerEvent<Item>