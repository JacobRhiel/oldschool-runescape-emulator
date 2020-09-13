package rs.emulator.entity.material.containers.events

import rs.emulator.entity.material.items.Item

/**
 *
 * @author javatar
 */

typealias Slot = Int

interface ItemContainerEvent<out I : Item> {

    val item: I
    var slot: Slot
    var ignored: Boolean

}