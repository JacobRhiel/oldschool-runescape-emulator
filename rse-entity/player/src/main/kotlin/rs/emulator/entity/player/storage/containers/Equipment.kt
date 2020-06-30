package rs.emulator.entity.player.storage.containers

import rs.emulator.entity.actor.player.storage.container.ItemContainer
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.items.Wearable

/**
 *
 * @author javatar
 */

class Equipment : ItemContainer<Wearable>(Array(14){ItemData.EMPTY_WEARABLE}, ItemData.EMPTY_WEARABLE) {
    override fun nextSlot(): Int {
        return EquipmentSlot.WEAPON.slot
    }
    override fun add(element: Wearable) {
        val mainSlot = element.mainSlot.slot
        val secondarySlot = element.secondarySlot.slot
        if(element.stackable && this[mainSlot] !== ItemData.EMPTY_WEARABLE) {
            val inSlot = this[mainSlot].copy()
            inSlot += element
            val event = inSlot.createEvent(mainSlot)
            addPublisher.onNext(event)
            if(!event.cancelled) {
                this[mainSlot] = inSlot
            }
        } else if(this[mainSlot] !== ItemData.EMPTY_WEARABLE) {
            val event = element.createEvent(mainSlot)
            addPublisher.onNext(event)
            if(!event.cancelled) {
                remove(this[mainSlot])
                this[mainSlot] = element.copy()
            }
        } else {
            val event = element.createEvent(mainSlot)
            addPublisher.onNext(event)
            if(!event.cancelled) {
                this[mainSlot] = element.copy()
            }
        }
        if(mainSlot != secondarySlot) {
            remove(this[secondarySlot])
        }
        addPublisher.onComplete()
    }

    override fun remove(element: Wearable) {
        val mainSlot = element.mainSlot.slot
        val secondarySlot = element.secondarySlot.slot
        val inSlot = this[mainSlot].copy()
        if(inSlot !== ItemData.EMPTY_WEARABLE) {
            if(element.stackable) {
                inSlot -= element
                val event = inSlot.copy().createEvent(mainSlot)
                removePublisher.onNext(event)
                if(!event.cancelled) {
                    if(event.item != inSlot) {
                        this[mainSlot] = event.item
                    } else {
                        this[mainSlot] = inSlot
                    }
                }
            } else {
                val event = inSlot.copy().createEvent(mainSlot)
                removePublisher.onNext(event)
                if(!event.cancelled) {
                    if(event.item != inSlot) {
                        this[mainSlot] = event.item
                    } else {
                        this[mainSlot] = ItemData.EMPTY_WEARABLE
                    }
                }
            }
        }
    }
}