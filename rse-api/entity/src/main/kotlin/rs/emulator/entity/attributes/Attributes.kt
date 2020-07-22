package rs.emulator.entity.attributes

/**
 *
 * @author javatar
 */

data class Attributes(val attributeMap: MutableMap<String, Attribute<*>> = mutableMapOf()) {

    inline operator fun <reified V> get(key: String): V {
        return attributeMap[key]!!.value as V
    }

    inline operator fun <reified V> set(key: String, value: Attribute<V>) {
        this.attributeMap[key] = value
    }

}