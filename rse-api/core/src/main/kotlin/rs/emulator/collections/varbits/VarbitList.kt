package rs.emulator.collections.varbits

import com.google.gson.Gson
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

class VarbitList {

    private val varbits : MutableMap<Int, Int> = mutableMapOf()

    fun id(id : Int) : VarbitDelegate {
        return VarbitDelegate(this, id, varbits.getOrDefault(id, 0))
    }

    operator fun invoke(id: Int): VarbitDelegate {
        return VarbitDelegate(this, id, this[id])
    }

    operator fun get(key: Int): Int = varbits.getOrDefault(key, 0)

    internal operator fun set(key: Int, value: Int) {
        varbits[key] = value
    }

    override fun toString(): String {
        return get<Gson>().toJson(this)
    }
}