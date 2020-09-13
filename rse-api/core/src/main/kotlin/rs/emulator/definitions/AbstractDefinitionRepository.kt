package rs.emulator.definitions

import rs.emulator.definitions.impl.widget.WidgetDefinition

/**
 *
 * @author javatar
 */

abstract class AbstractDefinitionRepository {

    abstract fun findActual(identifier: Int, child: Int, keys: IntArray? = null, clazz: Class<*>): Definition?

    abstract fun findWidget(identifier: Int): Array<WidgetDefinition>


    inline fun <reified T : Definition> find(identifier: Int, keys: IntArray? = null): T {
        return find(identifier, -1, keys, T::class.java) as T
    }

    inline fun <reified T : Definition> find(identifier: Int, child: Int, keys: IntArray? = null): T {
        return find(identifier, child, keys, T::class.java) as T
    }

    @PublishedApi
    internal fun find(identifier: Int, child: Int, keys: IntArray? = null, clazz: Class<*>) =
        this.findActual(identifier, child, keys, clazz)

}