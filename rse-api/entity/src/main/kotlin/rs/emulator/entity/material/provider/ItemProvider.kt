package rs.emulator.entity.material.provider

import rs.emulator.definitions.factories.ItemDefinitionFactory
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.factories.StandardItemFactory
import rs.emulator.entity.material.factories.WearableItemFactory
import rs.emulator.entity.material.items.Item

object ItemProvider {
    inline fun <reified T : Item> provide(id : Int, amount : Int = 1) : T {
        val def = ItemDefinitionFactory.provide(id)
        val meta = ItemMetaDefinitionFactory.provide(id)
        return if(def.equipSlot == -1) {
            StandardItemFactory.create {
                this.id = id
                this.amount = amount
                this.stackable = def.stackable
            } as T
        } else {
            WearableItemFactory.create {
                this.id = id
                this.amount = amount
                this.stackable = def.stackable
                this.mainSlot = EquipmentSlot.bySlot(def.equipSlot)
                this.secondarySlot = this.mainSlot
            } as T
        }
    }
}