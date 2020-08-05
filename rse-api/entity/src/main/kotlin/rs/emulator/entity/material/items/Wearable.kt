package rs.emulator.entity.material.items

import rs.emulator.definitions.factories.ItemDefinitionFactory
import rs.emulator.entity.material.EquipmentSlot

/**
 *
 * @author javatar
 */

class Wearable(
    var mainSlot: EquipmentSlot = EquipmentSlot.WEAPON,
    var secondarySlot: EquipmentSlot = mainSlot,
    id: Int = -1,
    amount: Int = 1,
    stackable: Boolean = mainSlot == EquipmentSlot.ARROWS
) : Item(id, amount, stackable) {

    override fun copy(amount: Int, stackable: Boolean): Wearable {
        return Wearable(this.mainSlot, this.secondarySlot, this.id, amount, stackable)
            .also { it.attributes.setAttributes(this.attributes.map) }
    }

    override fun toNoted(): Wearable {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.noteTemplateId == 0 && def.noteLinkId > 0) {
            Wearable(mainSlot, secondarySlot, def.noteLinkId, amount, stackable)
                .also { it.attributes.setAttributes(this.attributes.map) }
        } else {
            this.copy()
        }
    }

    override fun toUnnoted(): Wearable {
        val def = ItemDefinitionFactory.provide(this.id)
        return if (def.noteTemplateId > 0) Wearable(
            mainSlot,
            secondarySlot,
            def.noteLinkId,
            amount,
            stackable
        ) else this.copy()
    }


    override fun toString(): String {
        return "Wearable(mainSlot=$mainSlot, secondarySlot=$secondarySlot, id=$id, amount=$amount, stackable=$stackable)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Wearable

        if (mainSlot != other.mainSlot) return false
        if (secondarySlot != other.secondarySlot) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + mainSlot.hashCode()
        result = 31 * result + secondarySlot.hashCode()
        return result
    }
}