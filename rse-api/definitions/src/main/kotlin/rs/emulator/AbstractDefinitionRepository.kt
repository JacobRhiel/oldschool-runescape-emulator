package rs.emulator

import rs.emulator.definitions.Definition

/**
 *
 * @author javatar
 */

abstract class AbstractDefinitionRepository {

    protected abstract fun findActual(identifier: Int, child: Int, clazz : Class<*>): Definition?

    inline fun <reified T : Definition> find(identifier: Int, child: Int) : T {
        return find(identifier, child, T::class.java) as T
    }

    @PublishedApi internal fun find(identifier: Int, child: Int, clazz: Class<*>) = this.findActual(identifier, child, clazz)

}