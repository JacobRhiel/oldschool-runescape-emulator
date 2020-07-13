package rs.emulator.skills

import rs.emulator.skills.delegates.FakeLevelDelegate

/**
 *
 * @author javatar
 */

class SkillManager(private val skills: MutableMap<Int, Skill> = mutableMapOf()) {

    fun addExperience(id: Int, experience: Int) {
        val skill = skills[id]!!
        if (skill.experience < Integer.MAX_VALUE) {
            try {
                skill.experience = Math.addExact(skill.experience, experience)
            } catch (e: ArithmeticException) {
                skill.experience = Integer.MAX_VALUE
            }
            //TODO - Level up detection
        }
    }

    fun levelUp(skill: Skill) {
        if (skill.actualLevel < skill.maxLevel) {
            skill.actualLevel += 1
        }
    }

    operator fun invoke(id: Int): FakeLevelDelegate = FakeLevelDelegate(skills[id]!!)

    fun skill(id: Int) = this(id)

}