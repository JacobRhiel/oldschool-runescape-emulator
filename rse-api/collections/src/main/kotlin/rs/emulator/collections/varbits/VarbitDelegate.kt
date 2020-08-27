package rs.emulator.collections.varbits

import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

data class VarbitDelegate(private val list : VarbitList, val id : Int, val value : Int) {

    operator fun getValue(any: Any?, property: KProperty<*>): Int {
        return list[this.id]
    }

    operator fun setValue(any: Any?, property: KProperty<*>, i: Int) {
        list[this.id] = i
    }
}