package rs.emulator.entity.skills

import rs.emulator.skills.math.ExperienceMath

/**
 *
 * @author javatar
 */

data class Skill(
    val id : Int,
    var level : Int = if(id == 3) 10 else 1,
    var staticLevel : Int = level,
    var experience : Int = if(id == 3) ExperienceMath.xpForLevel(10) else 0
)