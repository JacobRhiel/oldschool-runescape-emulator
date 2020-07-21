package rs.emulator.collections.varbits

import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

data class VarbitDelegate(private val list : VarbitList, val id : Int, val value : Int) {

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Int {
        return list[this.id]
    }

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, i: Int) {
        list[this.id] = i
    }
}