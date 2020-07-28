package rs.emulator.entity.material.items

import rs.emulator.entity.material.EquipmentSlot

/**
 *
 * @author javatar
 */

class Wearable(
    var mainSlot: EquipmentSlot = EquipmentSlot.WEAPON,
    var secondarySlot: EquipmentSlot = mainSlot,
    @Transient var effectBlock: () -> Unit = {},
    id: Int = -1,
    amount: Int = 1,
    stackable: Boolean = false
) : Item(id, amount, stackable), WearableEffect {

    fun effect(block : () -> Unit) {
        effectBlock = block
    }

    override fun effect() {
        effectBlock()
    }

    override fun copy(amount: Int, stackable: Boolean): Wearable {
        return Wearable(this.mainSlot, this.secondarySlot, this.effectBlock, this.id, amount, stackable)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Wearable

        if (mainSlot != other.mainSlot) return false
        if (secondarySlot != other.secondarySlot) return false
        if (id != other.id) return false
        if (stackable != other.stackable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mainSlot.hashCode()
        result = 31 * result + secondarySlot.hashCode()
        result = 31 * result + id
        result = 31 * result + stackable.hashCode()
        return result
    }

    override fun toString(): String {
        return "Wearable(mainSlot=$mainSlot, secondarySlot=$secondarySlot, id=$id, amount=$amount, stackable=$stackable)"
    }
}