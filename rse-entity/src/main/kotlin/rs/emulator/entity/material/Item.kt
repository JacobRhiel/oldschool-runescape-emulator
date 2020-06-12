package rs.emulator.entity.material

/**
 *
 * @author javatar
 */

class Item(override var id: Int, override var amount: Int = 1, override var stackable: Boolean = false) : IItem {



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
        result = 31 * result + amount
        result = 31 * result + stackable.hashCode()
        return result
    }

    override fun copy(amount: Int, stackable: Boolean): Item {
        return Item(this.id, amount, stackable)
    }

    override fun toString(): String {
        return "Item(id=$id, amount=$amount, stackable=$stackable)"
    }
}