package rs.emulator.entity.material

import rs.emulator.entity.IEntity

/**
 *
 * @author javatar
 */

interface IItem : IEntity {

    val id : Int
    var amount : Int
    var stackable : Boolean

    fun copy(amount : Int = this.amount, stackable : Boolean = this.stackable) : IItem

    operator fun plusAssign(item : IItem) {
        this.amount += item.amount
    }

    operator fun minusAssign(item : IItem) {
        this.amount -= item.amount
    }

}