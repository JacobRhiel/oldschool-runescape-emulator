package rs.emulator.containers

import io.reactivex.disposables.Disposable

/**
 *
 * @author javatar
 */

abstract class Container<T, O>(val array: Array<T>, private val default : T) : MutableIterable<T> {

    abstract fun add(element: T)
    abstract fun remove(element: T)
    abstract infix fun onAdd(block : O.() -> Unit) : Disposable
    abstract infix fun onRemove(block : O.() -> Unit) : Disposable

    operator fun set(index: Int, element: T) {
        array[index] = element
    }

    operator fun get(index: Int): T = array[index]

    fun isFull() : Boolean = all { it !== default }
    fun isEmpty() : Boolean = all { it === default }

    override fun iterator(): MutableIterator<T> = object : MutableIterator<T> {
        var index = 0
        override fun hasNext(): Boolean {
            return index < array.size
        }

        override fun next(): T {
            return array[index++]
        }

        override fun remove() {
            array[index] = default
        }
    }
}