package rs.emulator.collections.varbits

import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

class VarbitList {

    private val varbits : MutableMap<Int, Int> = mutableMapOf()

    fun id(id : Int) : VarbitDelegate {
        return VarbitDelegate(this, id, varbits.getOrDefault(id, 0))
    }

    operator fun invoke(id : Int) : VarbitDelegate {
        return VarbitDelegate(this, id, this[id])
    }

    internal operator fun get(key : Int) : Int = varbits.getOrDefault(key, 0)
    internal operator fun set(key : Int, value : Int) {
        varbits[key] = value
    }

}