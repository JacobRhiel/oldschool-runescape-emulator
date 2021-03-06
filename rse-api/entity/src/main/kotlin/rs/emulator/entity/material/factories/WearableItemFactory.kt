package rs.emulator.entity.material.factories

import rs.emulator.entity.material.AbstractMaterialFactory
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.items.Wearable

/**
 *
 * @author javatar
 */

object WearableItemFactory : AbstractMaterialFactory<Wearable> {

    val map = mutableMapOf<Int, (Int, Int, Boolean) -> Wearable>(
        20 to { id, amt, stacks -> Wearable(EquipmentSlot.SHIELD, id = id, amount = amt, stackable = stacks) }
    )

    override fun create(id: Int, amount: Int, stackable: Boolean): Wearable {
        return map.getOrDefault(id) { _, _, _ ->
            Wearable(
                EquipmentSlot.WEAPON,
                id = id,
                amount = amount,
                stackable = stackable
            )
        }(id, amount, stackable)
    }

    override fun create(cache: Boolean, block: Wearable.() -> Unit): Wearable {
        val item = Wearable()
        block(item)
        if (cache) {
            map[item.id] = { _, amt, stacks -> item.copy(amt, stacks) }
        }
        return item
    }
}