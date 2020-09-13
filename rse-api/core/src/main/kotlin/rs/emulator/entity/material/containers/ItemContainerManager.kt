package rs.emulator.entity.material.containers

import rs.emulator.entity.material.containers.impl.Equipment
import rs.emulator.entity.material.containers.impl.Inventory
import rs.emulator.entity.material.containers.impl.bank.Bank

/**
 *
 * @author javatar
 */

class ItemContainerManager {

    val inventory: Inventory
        get() = containers[93] as Inventory
    val equipment: Equipment
        get() = containers[94] as Equipment
    val bank: Bank
        get() = containers[95] as Bank


    private val _containers = mutableMapOf<Int, ItemContainer<*>>()
    val containers: Map<Int, ItemContainer<*>> = _containers

    fun registerContainer(key: Int, container: ItemContainer<*>, block: ItemContainer<*>.() -> Unit = {}) {
        _containers[key] = container.also(block)
    }

    inline fun <reified C : ItemContainer<*>> container(key: Int): C =
        containers[key] as C ?: throw Error("No Container found for key $key")

}