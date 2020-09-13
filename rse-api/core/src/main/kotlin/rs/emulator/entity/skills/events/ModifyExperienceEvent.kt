package rs.emulator.entity.skills.events

import rs.emulator.entity.skills.SkillEvent
import rs.emulator.entity.skills.Skill

/**
 *
 * @author javatar
 */

data class ModifyExperienceEvent(override val source: Skill, val baseExperience : Int, val experience : Int, override val ignored: Boolean = false) : SkillEvent