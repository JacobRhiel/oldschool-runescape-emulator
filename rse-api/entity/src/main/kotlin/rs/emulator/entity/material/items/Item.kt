package rs.emulator.entity.material.items

import rs.emulator.entity.IEntity
import rs.emulator.region.WorldCoordinate

/**
 *
 * @author javatar
 */

abstract class Item(var id: Int, var amount: Int = 1, var stackable: Boolean = false) : IEntity {

    override val coordinate: WorldCoordinate = WorldCoordinate(-1, -1)
    override val lastCoordinate: WorldCoordinate = coordinate

    abstract fun copy(amount: Int = this.amount, stackable: Boolean = this.stackable): Item

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (stackable != other.stackable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + stackable.hashCode()
        return result
    }

    override fun toString(): String {
        return "Item(id=$id, amount=$amount, stackable=$stackable)"
    }

    operator fun plusAssign(item : Item) {
        this.amount += item.amount
    }

    operator fun minusAssign(item : Item) {
        this.amount -= item.amount
    }

}