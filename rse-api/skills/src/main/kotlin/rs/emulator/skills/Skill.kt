package rs.emulator.skills

/**
 *
 * @author javatar
 */

class Skill(
    val id: Int,
    var currentLevel: Int = 1,
    var actualLevel: Int = 1,
    var experience: Int = 0,
    val maxLevel: Int = 99
)