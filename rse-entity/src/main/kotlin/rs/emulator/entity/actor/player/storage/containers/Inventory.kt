package rs.emulator.entity.actor.player.storage.containers

import rs.emulator.entity.actor.player.storage.ItemContainer
import rs.emulator.entity.material.Item
import rs.emulator.entity.material.ItemData

/**
 *
 * @author javatar
 */

class Inventory : ItemContainer<Item>(Array(28){ItemData.EMPTY}, ItemData.EMPTY) {

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
                    addPublisher.onNext(found)
                    this[slot] = found
                } else {
                    val newSlot = nextSlot()
                    val item = element.copy()
                    addPublisher.onNext(item)
                    this[newSlot] = item
                }
            }
            else -> {
                var amt = element.amount
                var nextSlot = nextSlot()
                while(amt > 0 && !isFull() && nextSlot != -1) {
                    val item = element.copy(1)
                    addPublisher.onNext(item)
                    if(item.amount > 1) {
                        amt += (item.amount - 1)
                        item.amount = 1
                    }
                    this[nextSlot] = item
                    nextSlot = nextSlot()
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
                    removePublisher.onNext(found)
                    if(found.amount <= 0) {
                        this[slot] = ItemData.EMPTY
                    }
                }
                element.amount > 1 -> {
                    var amt = element.amount
                    var index = indexOf(element)
                    while(amt > 0 && index != -1) {
                        val item = this[index]
                        removePublisher.onNext(item)
                        if(item != this[index]) {
                            this[index] = item
                        } else {
                            this[index] = ItemData.EMPTY
                        }
                        index = indexOf(element)
                        amt--
                    }
                }
                else -> {
                    val index = indexOf(element)
                    if(index != -1) {
                        val item = this[index].copy()
                        removePublisher.onNext(item)
                        if(item != this[index]) {
                            this[index] = item
                        } else {
                            this[index] = ItemData.EMPTY
                        }
                    }
                }
            }
        }
        removePublisher.onComplete()
    }

    override fun nextSlot(): Int = indexOfFirst { it === ItemData.EMPTY }


}