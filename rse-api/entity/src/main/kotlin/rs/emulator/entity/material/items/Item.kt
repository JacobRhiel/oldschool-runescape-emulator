package rs.emulator.entity.material.items

import rs.emulator.entity.IEntity
import rs.emulator.entity.material.attributes.MaterialAttributes
import rs.emulator.region.WorldCoordinate

/**
 *
 * @author javatar
 */

abstract class Item(var id: Int, var amount: Int = 1, var stackable: Boolean = false) : IEntity {

    override val coordinate: WorldCoordinate = WorldCoordinate(-1, -1)

    @Transient
    override val lastCoordinate: WorldCoordinate = coordinate

    val attributes = MaterialAttributes()

    abstract fun copy(amount: Int = this.amount, stackable: Boolean = this.stackable): Item
    abstract fun toNoted(): Item
    abstract fun toUnnoted(): Item

    override fun toString(): String {
        return "Item(id=$id, amount=$amount, stackable=$stackable)"
    }

    operator fun plusAssign(item: Item) {
        val r = this.amount + item.amount
        val x = this.amount
        val y = item.amount
        if (x xor r and (y xor r) < 0) {
            this.amount = Integer.MAX_VALUE
        } else {
            this.amount = r
        }
    }

    operator fun minusAssign(item: Item) {
        val r = this.amount - item.amount
        val x = this.amount
        val y = item.amount
        if (x xor y and (x xor r) < 0) {
            this.amount = 0
        } else {
            this.amount = r
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (stackable != other.stackable) return false
        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + stackable.hashCode()
        result = 31 * result + attributes.hashCode()
        return result
    }

}