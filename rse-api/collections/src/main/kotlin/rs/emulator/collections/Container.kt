package rs.emulator.collections

import kotlinx.coroutines.flow.Flow

/**
 * A Container with a array of elements, mutating this container returns flows of type T
 * @author javatar
 */

interface Container<E, T> : Iterable<E> {
    fun add(element: E): Flow<T>
    fun remove(element: E): Flow<T>
}