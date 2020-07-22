package rs.emulator.entity.attributes

/**
 *
 * @author javatar
 */

data class Attribute<T>(val value: T, val persistent: Boolean = false)