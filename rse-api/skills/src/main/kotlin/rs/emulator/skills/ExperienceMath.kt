package rs.emulator.skills

import kotlin.math.floor
import kotlin.math.pow

object ExperienceMath {

    val LEVEL_EXPERIENCES = IntArray(127).apply {
        var points = 0.0
        IntRange(0, 126).forEach {
            val lvl = it + 1
            points += floor(lvl + 300 * 2.0.pow(lvl / 7.0))
            this[it] = (points / 4).toInt()
        }
    }

    fun levelForXp(xp: Double): Int {
        var level = 1
        for (i in 0..97) {
            if (xp >= LEVEL_EXPERIENCES[i]) level = i + 2
        }
        return level
    }

    fun xpForLevel(level: Int): Int {
        return if (level <= 1) 0 else LEVEL_EXPERIENCES[level - 2]
    }

}