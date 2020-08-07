package rs.emulator.entity.material.containers.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.containers.ItemContainer
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.AddContainerEvent
import rs.emulator.entity.material.containers.events.impl.NoSuchItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.RemoveContainerEvent
import rs.emulator.entity.material.containers.events.impl.StackedContainerEvent
import rs.emulator.entity.material.items.Wearable

/**
 *
 * @author javatar
 */

class Equipment : ItemContainer<Wearable>(94, Array(14) { ItemData.EMPTY_WEARABLE }) {
    override fun add(element: Wearable): Flow<ItemContainerEvent<Wearable>> = flow {
        val mainSlot = element.mainSlot.slot
        val secondarySlot = element.secondarySlot.slot
        when {
            element.stackable && elements[mainSlot] !== ItemData.EMPTY_WEARABLE -> {
                val inSlot = elements[mainSlot].copy()
                inSlot += element
                val event = StackedContainerEvent(true, mainSlot, inSlot)
                emit(event)
                if (!event.ignored) {
                    elements[event.slot] = inSlot
                }
            }
            elements[mainSlot] !== ItemData.EMPTY_WEARABLE -> {
                val event = AddContainerEvent(mainSlot, element)
                emit(event)
                if (!event.ignored) {
                    emitAll(remove(elements[mainSlot]))
                    elements[mainSlot] = element.copy()
                }
            }
            else -> {
                val event = AddContainerEvent(mainSlot, element)
                emit(event)
                if (!event.ignored) {
                    elements[mainSlot] = element.copy()
                }
            }
        }
        if (mainSlot != secondarySlot) {
            emitAll(remove(elements[secondarySlot]))
        }
    }

    override fun remove(element: Wearable): Flow<ItemContainerEvent<Wearable>> = flow {
        val mainSlot = element.mainSlot.slot
        val inSlot = elements[mainSlot]
        if (inSlot in elements) {
            when {
                element.stackable -> {
                    inSlot -= element
                    val event = StackedContainerEvent(true, mainSlot, inSlot)
                    emit(event)
                    if (!event.ignored) {
                        if (inSlot.amount <= 0) {
                            elements[mainSlot] = ItemData.EMPTY_WEARABLE
                        } else {
                            elements[mainSlot] = inSlot
                        }
                    }
                }
                else -> {
                    val event = RemoveContainerEvent(inSlot, mainSlot)
                    emit(event)
                    if (!event.ignored) {
                        elements[mainSlot] = ItemData.EMPTY_WEARABLE
                    }
                }
            }
        } else {
            emit(NoSuchItemContainerEvent(element, mainSlot))
        }
    }
}