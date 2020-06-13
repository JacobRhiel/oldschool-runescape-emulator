package rs.emulator.entity.material

import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable

object ItemData {

    val EMPTY = object : Item(-1, 1, false) {
        override fun copy(amount: Int, stackable: Boolean) : Item {
            return this
        }
    }

    val EMPTY_WEARABLE: Wearable = Wearable()

}