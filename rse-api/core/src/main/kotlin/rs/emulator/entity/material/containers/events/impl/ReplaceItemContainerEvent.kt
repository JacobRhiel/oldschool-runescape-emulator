package rs.emulator.entity.material.containers.events.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.Slot
import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */
@ExperimentalCoroutinesApi
data class ReplaceItemContainerEvent(override val item: Item, val replacedItem: Item, override var slot: Slot = -1, override var ignored: Boolean = false) : ItemContainerEvent<Item>