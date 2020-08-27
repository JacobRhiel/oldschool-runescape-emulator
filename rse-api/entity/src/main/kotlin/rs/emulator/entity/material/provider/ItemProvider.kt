package rs.emulator.entity.material.provider

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.definitions.factories.ItemDefinitionFactory
import rs.emulator.definitions.factories.ItemMetaDefinitionFactory
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.factories.StandardItemFactory
import rs.emulator.entity.material.factories.WearableItemFactory
import rs.emulator.entity.material.items.Item

object ItemProvider {
    @ExperimentalCoroutinesApi
    inline fun <reified T : Item> provide(id : Int, amount : Int = 1) : T {
        val def = ItemDefinitionFactory.provide(id)
        val meta = ItemMetaDefinitionFactory.provide(id)
        return if (meta.equipment.slot.isEmpty()) {
            StandardItemFactory.create {
                this.id = id
                this.amount = amount
                this.stackable = def.stackable
                this.attributes["tradeable"] = meta.tradeable
            } as T
        } else {
            WearableItemFactory.create {
                this.id = id
                this.amount = amount
                this.stackable = def.stackable
                this.mainSlot = EquipmentSlot.valueOf(meta.equipment.slot.toUpperCase())
                this.secondarySlot = this.mainSlot
                this.attributes["tradeable"] = meta.tradeable
            } as T
        }
    }
}