import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.player.storage.containers.Inventory
import rs.emulator.entity.material.Item

class ItemContainerTest {

    @Test
    fun addStackableItemTest() {

        val inv = Inventory()

        inv.add(Item(995, 1000, true))

        Assertions.assertTrue(inv[0].id == 995, "Failed to add coins")

        inv.add(Item(995, 1000, true))

        Assertions.assertTrue(inv[0].amount == 2000, "Failed to stack amount")

    }

    @Test
    fun addNonStackableItemTest() {

        val inv = Inventory()

        inv.add(Item(4151))

        Assertions.assertTrue(inv[0].id == 4151, "Failed to add whip")

        inv.add(Item(1050, 2))

        Assertions.assertTrue(inv.count { it.id == 1050 } == 2, "Failed to add non-stackables")

    }

    @Test
    fun publisherTest() {

        val inv = Inventory()

        inv.subscribeOnceOnAdd {
            Assertions.assertTrue(it.id == 4151, "Failed to sub onAdd")
        }

        inv onAdd {
            onNext {
                Assertions.assertTrue(it.id == 4151, "failed to add observer onNext")
            }
            onComplete {
                Assertions.assertTrue(inv.count() == 28, "Failed to call onComplete")
            }
        }

        inv.add(Item(4151))

    }

    @Test
    fun `add non-stackable item with once time subscribe`() {

        val inv = Inventory()

        inv.subscribeOnceOnAdd {
            if (it.id == 4151) {
                it.amount += 2
            }
        }

        inv.add(Item(4151))

        Assertions.assertTrue(inv.count { it.id == 4151 } == 3, "publisher failed to add two more whips. ${inv.count { it.id == 4151 }}")

    }

    @Test
    fun `remove stackable item`() {

        val inv = Inventory()

        inv.add(Item(995, 1000, true))

        inv.remove(Item(995, 500, true))

        Assertions.assertEquals(Item(995, 500, true), inv[0], "Failed to remove coins")

    }

    @Test
    fun `remove non stackable item`() {

        val inv = Inventory()

        inv.add(Item(1050, 5))

        inv.remove(Item(1050, 3))

        Assertions.assertTrue(inv.count { it.id == 1050 } == 2, "Failed to remove santa hats")

    }

    @Test
    fun `transform item on remove`() {
        val inv = Inventory()
        inv.subscribeOnceOnRemove {
            if(it.id == 1) {
                it.id = 1050
            }
        }
        inv.add(Item(1))
        inv.remove(Item(1))
        Assertions.assertEquals(Item(1050), inv[0], "Transformation failed.")
    }

    @Test
    fun `swap items in slots`() {

        val inv = Inventory()

        inv.add(Item(4151))

        inv.add(Item(1050))

        inv.swap(0, 27)

        Assertions.assertEquals(Item(4151), inv[27], "Failed swap to empty slot")

        inv.swap(1, 27)

        Assertions.assertEquals(Item(4151), inv[1], "Failed swap of whip and santa")
        Assertions.assertEquals(Item(1050), inv[27], "Failed swap to 27")

    }

}