import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.Test
import rs.emulator.entity.material.EquipmentSlot
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.containers.events.impl.AddContainerEvent
import rs.emulator.entity.material.containers.events.impl.RemoveContainerEvent
import rs.emulator.entity.material.containers.events.impl.StackedContainerEvent
import rs.emulator.entity.material.containers.impl.Equipment
import rs.emulator.entity.material.containers.impl.Inventory
import rs.emulator.entity.material.containers.invalidateState
import rs.emulator.entity.material.containers.toContainer
import rs.emulator.entity.material.containers.toEquipment
import rs.emulator.entity.material.items.StandardItem
import rs.emulator.entity.material.items.Wearable

@ExperimentalCoroutinesApi
class ContainerTest {

    @Test
    fun test() {

        val inv = Inventory()

        inv.addItem(StandardItem(4151))

        inv.add(StandardItem(4151))
            .filterIsInstance<AddContainerEvent<*>>()
            .onEach { println("Add $it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))


        assert(inv.elements.count { it.id == 4151 } == 2)

    }

    @Test
    fun amountTest() {

        val inv = Inventory()

        inv.add(StandardItem(4151, 2))
            .filterIsInstance<AddContainerEvent<*>>()
            .onEach { println(it) }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(inv.elements.count { it.id == 4151 } == 2)

    }

    @Test
    fun stackableTest() {

        val inv = Inventory()

        inv.addItem(StandardItem(995, 100, true))

        inv.add(StandardItem(995, 100, true))
            .filterIsInstance<StackedContainerEvent<*>>()
            .onEach { println("Modified $it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(inv.elements.first { it.id == 995 }.amount == 200)

    }

    @Test
    fun equipmentAdd() {

        val e = Equipment()

        e.add(Wearable(EquipmentSlot.WEAPON, id = 4151))
            .filterIsInstance<AddContainerEvent<Wearable>>()
            .onEach { println("Added whip $it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(e.elements[EquipmentSlot.WEAPON.slot].id == 4151)

        e.addItem(Wearable(EquipmentSlot.SHIELD, id = 2))
        e.add(Wearable(EquipmentSlot.WEAPON, EquipmentSlot.SHIELD, id = 1))
            .onEach {
                when (it) {
                    is AddContainerEvent<Wearable> -> {
                        println("Added $it")
                    }
                    is RemoveContainerEvent<Wearable> -> {
                        println("Removed $it")
                    }
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(e.elements[EquipmentSlot.SHIELD.slot] === ItemData.EMPTY_WEARABLE)
        assert(e.elements[EquipmentSlot.WEAPON.slot].id == 1)

        e.add(Wearable(id = 4151))
            .onEach { println(it) }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(e.elements[EquipmentSlot.WEAPON.slot].id == 4151)

    }

    @Test
    fun toContainerTest() {

        val inv = Inventory()
        val e = Equipment()

        e.addItem(Wearable(id = 4151))

        e.remove(Wearable(id = 4151))
            .onEach { println("Equipment events $it") }
            .toContainer(inv)
            .onEach { println("Inv events $it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(Wearable(id = 4151) in inv)

        inv.remove(Wearable(id = 4151))
            .onEach { println("Inv events $it") }
            .toEquipment(e)
            .onEach { println("Equipment events $it") }
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

        assert(e.elements[EquipmentSlot.WEAPON.slot].id == 4151)

    }

    @Test
    fun stateTest() {

        val inv = Inventory()

        inv.containerState.onEach {
            println("Updating state of container ${it.container.key}")
        }.launchIn(CoroutineScope(Dispatchers.Unconfined))

        inv.add(Wearable(id = 4151))
            .onEach { println(it) }
            .invalidateState()
            .launchIn(CoroutineScope(Dispatchers.Unconfined))

    }

}