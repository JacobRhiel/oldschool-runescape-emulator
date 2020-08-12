package rs.emulator.entity.material.containers

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import rs.emulator.collections.Container
import rs.emulator.entity.material.ItemData
import rs.emulator.entity.material.attributes.MaterialAttributes
import rs.emulator.entity.material.containers.events.ItemContainerEvent
import rs.emulator.entity.material.items.Item
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.utilities.koin.get

/**
 * [ItemContainer] - mutating this container returns a flow of given [ItemContainerEvent]s
 * The return flows of [add] and [remove] are cold flows
 * @author javatar
 */
@ExperimentalCoroutinesApi
abstract class ItemContainer<I : Item>(val key: Int, val elements: Array<I>) : Container<I, ItemContainerEvent<I>> {

    val nextSlot: Int get() = elements.indexOfFirst { it === ItemData.EMPTY }
    val attributes = MaterialAttributes()

    @Transient
    val containerState: MutableStateFlow<ContainerState> = MutableStateFlow(ContainerState(false, this))

    fun addItem(element: I) = add(element).invalidateState().launchIn(get<ActorScope>())
    fun removeItem(element: I) = remove(element).invalidateState().launchIn(get<ActorScope>())

    fun isFull(): Boolean = all { it !== ItemData.EMPTY }
    fun isEmpty(): Boolean = all { it === ItemData.EMPTY }

    fun updateState() {
        containerState.value = containerState.value.invalidate()
    }

    fun removeAll() = flow {
        val list = elements.toList()
        list.forEach { i ->
            emitAll(remove(i))
        }
    }

    override fun iterator(): Iterator<I> {
        return elements.iterator()
    }

    override fun toString(): String {
        return get<Gson>().toJson(this)
    }
}