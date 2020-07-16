package rs.emulator.skills

import io.reactivex.processors.PublishProcessor

/**
 *
 * @author javatar
 */

class SkillAttributes(attributeCount: Int = 23) {

    val skills = Array(attributeCount) { Skill(it) }
    val experienceProcessor = PublishProcessor.create<ExperienceEvent>()
    val levelUpProcessor = PublishProcessor.create<LevelEvent>()
    val attributeChangedProcessor = PublishProcessor.create<Skill>()

    fun addExperience(id: Int, experience: Int) {
        val event = ExperienceEvent(id, experience)
        experienceProcessor.offer(event)
        val skill = skills[event.id]
        val exp = skill.experience
        try {
            skill.experience = Math.addExact(exp, event.modifiedExperience)
        } catch (e: ArithmeticException) {
            skill.experience = Integer.MAX_VALUE
            attributeChangedProcessor.offer(skill)
            return
        }
        val levelForXp = ExperienceMath.levelForXp(skill.experience.toDouble())
        if (levelForXp > skill.actualLevel) {
            val levelEvent = LevelEvent(skill.id, skill.actualLevel, levelForXp)
            if (levelUpProcessor.offer(levelEvent) && skill.actualLevel < skill.maxLevel) {
                skill.actualLevel++
            }
        }
        attributeChangedProcessor.offer(skill)
    }

    fun setAttribute(experienceEvent: ExperienceEvent? = null, levelEvent: LevelEvent? = null, show: Boolean = false) {
        if (levelEvent != null) {
            val lvl = when {
                levelEvent.currentLevel > levelEvent.newLevel -> levelEvent.currentLevel
                else -> levelEvent.newLevel
            }
            val experience = ExperienceMath.xpForLevel(lvl)
            if (show) {
                skills[levelEvent.id].currentLevel = lvl
                addExperience(levelEvent.id, experience)
            } else {
                skills[levelEvent.id].actualLevel = lvl
                skills[levelEvent.id].currentLevel = lvl
                skills[levelEvent.id].experience = experience
            }
        } else if (experienceEvent != null) {
            val exp = when {
                experienceEvent.modifiedExperience > experienceEvent.baseExperience -> experienceEvent.modifiedExperience
                else -> experienceEvent.baseExperience
            }
            val lvl = ExperienceMath.levelForXp(exp.toDouble())
            if (show) {
                skills[experienceEvent.id].currentLevel = lvl
                addExperience(experienceEvent.id, exp)
            } else {
                skills[experienceEvent.id].actualLevel = lvl
                skills[experienceEvent.id].currentLevel = lvl
                skills[experienceEvent.id].experience = exp
            }
        }
    }

}