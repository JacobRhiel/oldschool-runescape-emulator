import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.player.storage.containers.Inventory
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.factories.StandardItemFactory
import rs.emulator.entity.material.factories.WearableItemFactory

class InventoryTest {

    @Test
    fun addStackableItemTest() {

        val factory = StandardItemFactory()
        val inv = Inventory()

        val coins = factory.create(995, 1000, true)

        inv.add(coins)

        Assertions.assertTrue(inv[0].id == 995, "Failed to add coins")

        val coins2 = factory.create {
            id = 995
            amount = 1000
            stackable = true
        }

        inv.add(coins2)

        Assertions.assertTrue(inv[0].amount == 2000, "Failed to stack amount")

    }

    @Test
    fun addNonStackableItemTest() {

        val factory = WearableItemFactory()
        val inv = Inventory()

        val whip = factory.create {
            id = 4151
            mainSlot = EquipmentSlot.WEAPON
            secondarySlot = EquipmentSlot.WEAPON
        }

        inv.add(whip)

        Assertions.assertTrue(inv[0].id == 4151, "Failed to add whip")

        val santas = factory.create {
            id = 1050
            amount = 2
            mainSlot = EquipmentSlot.HEAD
            secondarySlot = EquipmentSlot.HEAD
        }

        inv.add(santas)

        Assertions.assertTrue(inv.count { it.id == 1050 } == 2, "Failed to add non-stackables")

    }

    @Test
    fun publisherTest() {

        val factory = WearableItemFactory()
        val inv = Inventory()

        inv.subscribeOnceOnAdd {
            Assertions.assertTrue(it.item.id == 4151, "Failed to sub onAdd")
        }

        inv onAdd {

            onNext {
                Assertions.assertTrue(it.item.id == 4151, "failed to add observer onNext")
            }
            onComplete {
                Assertions.assertTrue(inv.count() == 28, "Failed to call onComplete")
            }
        }

        val whip = factory.create {
            id = 4151
            mainSlot = EquipmentSlot.WEAPON
            secondarySlot = EquipmentSlot.WEAPON
        }

        inv.add(whip)

    }

    @Test
    fun `add non-stackable item with once time subscribe`() {

        val factory = WearableItemFactory()
        val inv = Inventory()

        inv.subscribeOnceOnAdd {
            if (it.item.id == 4151) {
                it.item.amount += 2
            }
        }

        val whip = factory.create {
            id = 4151
            mainSlot = EquipmentSlot.WEAPON
            secondarySlot = EquipmentSlot.WEAPON
        }

        inv.add(whip)

        Assertions.assertTrue(inv.count { it.id == 4151 } == 3, "publisher failed to add two more whips. ${inv.count { it.id == 4151 }}")

    }

    @Test
    fun `remove stackable item`() {

        val factory = StandardItemFactory()
        val inv = Inventory()

        val coins1 = factory.create {
            id = 995
            amount = 1000
            stackable = true
        }

        inv.add(coins1)

        val coins2 = factory.create {
            id = 995
            amount = 500
            stackable = true
        }

        inv.remove(coins2)

        Assertions.assertEquals(coins2, inv[0], "Failed to remove coins")

    }

    @Test
    fun `remove non stackable item`() {

        val factory = WearableItemFactory()
        val inv = Inventory()

        val santa = factory.create {
            id = 1050
            amount = 5
            mainSlot = EquipmentSlot.HEAD
            secondarySlot = EquipmentSlot.HEAD
        }

        inv.add(santa)

        inv.remove(santa.copy(3))

        Assertions.assertTrue(inv.count { it.id == 1050 } == 2, "Failed to remove santa hats")

    }

    @Test
    fun `transform item on remove`() {
        val factory = WearableItemFactory()
        val inv = Inventory()
        inv.subscribeOnceOnRemove {
            if(it.item.id == 1) {
                it.item.id = 1050
            }
        }
        val item = factory.create {
            id = 1
        }
        inv.add(item)
        inv.remove(item.copy())

        Assertions.assertEquals(factory.create(1050), inv[0], "Transformation failed.")
    }

    @Test
    fun `swap items in slots`() {

        val factory = WearableItemFactory()
        val sfactory = StandardItemFactory()
        val inv = Inventory()

        val whip = factory.create {
            id = 4151
            mainSlot = EquipmentSlot.WEAPON
            secondarySlot = EquipmentSlot.WEAPON
        }
        val santa = sfactory.create {
            id = 1050
        }

        inv.add(whip)

        inv.add(santa)

        inv.swap(0, 27)

        Assertions.assertEquals(whip.copy(), inv[27], "Failed swap to empty slot")

        inv.swap(1, 27)

        Assertions.assertEquals(whip.copy(), inv[1], "Failed swap of whip and santa")
        Assertions.assertEquals(santa.copy(), inv[27], "Failed swap to 27")

    }

}