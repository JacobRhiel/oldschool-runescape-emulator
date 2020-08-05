package rs.emulator.collections

import kotlinx.coroutines.flow.Flow

/**
 * A Container with a array of elements, mutating this container returns flows of type T
 * @author javatar
 */

abstract class Container<E, T>(val elements: Array<E>) : Iterable<E> {

    abstract fun add(element: E): Flow<T>
    abstract fun remove(element: E): Flow<T>

    override fun iterator(): Iterator<E> {
        return elements.iterator()
    }
}