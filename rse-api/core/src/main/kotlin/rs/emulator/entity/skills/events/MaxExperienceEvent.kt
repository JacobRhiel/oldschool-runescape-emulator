package rs.emulator.entity.skills.events

import rs.emulator.entity.skills.Skill
import rs.emulator.entity.skills.SkillEvent

/**
 * This event cannot be ignored
 * @author javatar
 */

data class MaxExperienceEvent(override val source: Skill, override val ignored: Boolean = false) : SkillEvent