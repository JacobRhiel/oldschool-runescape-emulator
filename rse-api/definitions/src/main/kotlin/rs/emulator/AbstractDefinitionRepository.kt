package rs.emulator

import rs.emulator.definitions.Definition

/**
 *
 * @author javatar
 */

abstract class AbstractDefinitionRepository {

    protected abstract fun findActual(identifier: Int, child: Int, keys: IntArray? = null, clazz : Class<*>): Definition?

    inline fun <reified T : Definition> find(identifier: Int, keys: IntArray? = null) : T {
        return find(identifier, -1, keys, T::class.java) as T
    }

    inline fun <reified T : Definition> find(identifier: Int, child: Int, keys: IntArray? = null) : T {
        return find(identifier, child, keys, T::class.java) as T
    }

    @PublishedApi internal fun find(identifier: Int, child: Int, keys: IntArray? = null, clazz: Class<*>) = this.findActual(identifier, child, keys, clazz)

}