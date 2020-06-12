import org.junit.jupiter.api.Test
import rs.emulator.entity.actor.player.storage.ItemContainer
import rs.emulator.entity.material.IItem

class ContainerTest {

    @Test
    fun test() {

        data class Item(override val id: Int, override var amount: Int, override var stackable: Boolean) : IItem {
            override fun copy(amount: Int, stackable: Boolean): IItem {
                return Item(this.id, amount, stackable)
            }
        }

        val empty = Item(-1, 0, false)
        class Inventory : ItemContainer<Item>(Array(28){empty}, empty) {
            override fun nextSlot(): Int {
                TODO("Not yet implemented")
            }

            override fun add(element: Item) {
                TODO("Not yet implemented")
            }

            override fun remove(element: Item) {
                TODO("Not yet implemented")
            }

        }
    }
}