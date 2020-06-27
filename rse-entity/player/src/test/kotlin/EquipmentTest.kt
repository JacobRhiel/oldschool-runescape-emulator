import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.items.Wearable
import rs.emulator.entity.player.storage.containers.Equipment
import rs.emulator.entity.player.storage.containers.Inventory

class EquipmentTest {

    @Test
    fun `add none stackable wearable item`() {

        val eq = Equipment()

        val whip = Wearable(EquipmentSlot.WEAPON, id = 4151)

        eq.add(whip)

        Assertions.assertEquals(whip, eq[EquipmentSlot.WEAPON.slot], "Failed to add whip to slot 3")

    }

    @Test
    fun `add stackable wearable item`() {

        val eq = Equipment()

        val fakeArrows = Wearable(
            EquipmentSlot.ARROWS,
            id = 5,
            amount = 100,
            stackable = true
        )

        eq.add(fakeArrows)

        Assertions.assertEquals(fakeArrows, eq[EquipmentSlot.ARROWS.slot], "Failed to add stackable wearable item")

        val moreFakeArrows = Wearable(
            EquipmentSlot.ARROWS,
            id = 5,
            amount = 150,
            stackable = true
        )

        eq.add(moreFakeArrows)

        Assertions.assertTrue(eq[EquipmentSlot.ARROWS.slot].amount == 250, "Failed to increment stackable wearable item")

    }

    @Test
    fun `add secondary slot whip`() {

        val eq = Equipment()

        val whip =
            Wearable(
                EquipmentSlot.WEAPON,
                EquipmentSlot.SHIELD,
                id = 4151
            )
        val fakeShield = Wearable(EquipmentSlot.SHIELD, id = 20)
        eq.onRemove {
            onNext {
                println("Removed Shield ${it.item.id}")
            }
        }
        eq.add(fakeShield)
        eq.add(whip)
        Assertions.assertTrue(eq[EquipmentSlot.SHIELD.slot] === ItemData.EMPTY_WEARABLE, "Failed to remove shield")


    }

    @Test
    fun `add back to inventory test`() {

        val inv = Inventory()
        val eq = Equipment()

        eq.apply {
            eq.onAdd {
                onNext {
                    val item = it.item
                    if(item in inv) {
                        inv.remove(item)
                    } else {
                        it.cancelled = true
                    }
                }
            } and onRemove {
                onNext {
                    val item = it.item
                    if(inv.isFull()) {
                        it.cancelled = true
                    } else {
                        inv.add(item)
                    }
                }
            }
        }



    }

}