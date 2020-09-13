package rs.emulator.entity.actor.attributes

/**
 *
 * @author javatar
 */

interface Attributes<V> {

    operator fun get(key: String): V
    operator fun set(key: String, value: V)

}