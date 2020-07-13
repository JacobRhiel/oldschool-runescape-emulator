package rs.emulator.skills.delegates

import rs.emulator.skills.Skill
import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

class FakeLevelDelegate(private val skill: Skill) {

    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Int {
        return skill.fakeLevel
    }

    operator fun setValue(nothing: Nothing?, property: KProperty<*>, value: Int) {
        skill.fakeLevel = value
    }

}