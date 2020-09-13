package rs.emulator.entity.skills.events

import rs.emulator.entity.skills.Skill
import rs.emulator.entity.skills.SkillEvent

/**
 *
 * @author javatar
 */

data class LevelUpEvent(override val source: Skill, val oldLevel : Int, val newLevel : Int, val restoreLevel : Boolean = false, override val ignored: Boolean = false) : SkillEvent