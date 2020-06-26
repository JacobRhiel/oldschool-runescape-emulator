package rs.emulator.entity.player.storage.containers

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.ItemData

/**
 *
 * @author javatar
 */

class Inventory : ItemContainer<Item>(Array(28){ ItemData.EMPTY}, ItemData.EMPTY) {

    override fun add(element: Item) {
        if(isFull()) {
            return
        }
        when {
            element.stackable -> {
                val slot = indexOf(element)
                if(slot != -1) {
                    val found = this[slot].copy()
                    found += element
                    val event = found.createEvent()
                    addPublisher.onNext(event)
                    if (!event.cancelled) {
                        this[slot] = found
                    }
                } else {
                    val newSlot = nextSlot()
                    val item = element.copy()
                    val event = item.createEvent()
                    addPublisher.onNext(event)
                    if (!event.cancelled) {
                        this[newSlot] = item
                    }
                }
            }
            else -> {
                var amt = element.amount
                var nextSlot = nextSlot()
                while(amt > 0 && !isFull() && nextSlot != -1) {
                    val item = element.copy(1)
                    val event = item.createEvent()
                    addPublisher.onNext(event)
                    if (!event.cancelled) {
                        if(item.amount > 1) {
                            amt += (item.amount - 1)
                            item.amount = 1
                        }
                        this[nextSlot] = item
                        nextSlot = nextSlot()
                    }
                    amt--
                }
            }
        }
        addPublisher.onComplete()
    }

    override fun remove(element: Item) {
        if(isEmpty())
            return
        val slot = indexOf(element)
        if (slot != -1 && this[slot] !== ItemData.EMPTY) {
            when {
                element.stackable -> {
                    val found = this[slot]
                    found -= element
                    val event = found.createEvent()
                    removePublisher.onNext(event)
                    if(!event.cancelled && found.amount <= 0) {
                        this[slot] = ItemData.EMPTY
                    }
                }
                element.amount > 1 -> {
                    var amt = element.amount
                    var index = indexOf(element)
                    while(amt > 0 && index != -1) {
                        val item = this[index]
                        val event = item.createEvent()
                        removePublisher.onNext(event)
                        if (!event.cancelled) {
                            if(item != this[index]) {
                                this[index] = item
                            } else {
                                this[index] = ItemData.EMPTY
                            }
                            index = indexOf(element)
                        }
                        amt--
                    }
                }
                else -> {
                    val index = indexOf(element)
                    if(index != -1) {
                        val item = this[index].copy()
                        val event = item.createEvent()
                        removePublisher.onNext(event)
                        if (!event.cancelled) {
                            if(item != this[index]) {
                                this[index] = item
                            } else {
                                this[index] = ItemData.EMPTY
                            }
                        }
                    }
                }
            }
        }
        removePublisher.onComplete()
    }

    override fun nextSlot(): Int = indexOfFirst { it === ItemData.EMPTY }


}