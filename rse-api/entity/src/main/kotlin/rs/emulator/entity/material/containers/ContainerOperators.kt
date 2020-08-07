package rs.emulator.entity.material.containers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.material.attributes.MaterialAttributes
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.containers.events.impl.RequestContainerAttributesEvent
import rs.emulator.entity.material.containers.events.impl.UpdateContainerStateEvent
import rs.emulator.entity.material.containers.impl.Equipment
import rs.emulator.entity.material.items.Item
import rs.emulator.entity.material.items.Wearable

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
fun <I : Item> Flow<ItemContainerEvent<I>>.toContainer(container: ItemContainer<I>) = flow {
    this@toContainer.filterNot { it.ignored }.collect { this.emitAll(container.add(it.item)) }
}

fun <I : Item> Flow<ItemContainerEvent<I>>.settings(block: (MaterialAttributes) -> Unit): Flow<ItemContainerEvent<I>> =
    flow {
        this@settings.collect {
            if (it is RequestContainerAttributesEvent) {
                block(it.attributes)
            } else {
                emit(it)
            }
        }
    }

@ExperimentalCoroutinesApi
fun Flow<ItemContainerEvent<Item>>.invalidateState() = flow {
    this@invalidateState.collect {
        if (it is UpdateContainerStateEvent) {
            it.container.updateState()
        } else {
            emit(it)
        }
    }
}

fun Flow<ItemContainerEvent<Item>>.ignoreWhile(predicate: (ItemContainerEvent<Item>) -> Boolean) = flow {
    this@ignoreWhile.collect { if (!predicate(it)) emit(it) }
}

fun Flow<ItemContainerEvent<Item>>.toEquipment(container: Equipment) = flow {
    this@toEquipment.filter { it.item is Wearable && !it.ignored }
        .collect { this.emitAll(container.add(it.item as Wearable)) }
}

fun IPlayer.inventory() = containerManager.inventory
fun IPlayer.equipment() = containerManager.equipment