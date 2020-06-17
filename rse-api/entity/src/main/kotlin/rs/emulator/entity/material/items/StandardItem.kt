package rs.emulator.entity.material.items

/**
 *
 * @author javatar
 */

class StandardItem(id: Int, amount : Int = 1, stackable : Boolean = false) : Item(id, amount, stackable) {
    override fun copy(amount: Int, stackable: Boolean): Item {
        return StandardItem(this.id, amount, stackable)
    }
}