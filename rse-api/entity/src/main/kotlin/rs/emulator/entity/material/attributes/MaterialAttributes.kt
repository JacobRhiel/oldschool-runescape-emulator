package rs.emulator.entity.material.attributes

import com.google.gson.Gson
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class MaterialAttributes(private val _map: MutableMap<String, Any> = mutableMapOf()) {

    val map: Map<String, Any> = _map

    operator fun set(key: String, value: Any) {
        _map[key] = value
    }

    inline operator fun <reified V> get(key: String): V = map[key] as V

    fun setAttributes(map: Map<String, Any>) {
        _map.clear()
        _map.putAll(map)
    }

    fun mergeAttributes(map: Map<String, Any>) {
        _map.putAll(map)
    }

    override fun toString(): String {
        return get<Gson>().toJson(_map)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MaterialAttributes

        if (_map != other._map) return false

        return true
    }

    override fun hashCode(): Int {
        return _map.hashCode()
    }


}