package rs.emulator.entity.skills

import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import rs.emulator.entity.skills.events.LevelUpEvent
import rs.emulator.entity.skills.events.MaxExperienceEvent
import rs.emulator.entity.skills.events.ModifyExperienceEvent
import rs.emulator.skills.Skills
import rs.emulator.skills.math.ExperienceMath
import rs.emulator.utilities.koin.get

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class SkillManager(val maxExperience : Int = ExperienceMath.MAX_EXPERIENCE) {

    val skills = Array(Skills.values().size) { Skill(it) }

    @Transient val skillState = MutableStateFlow<List<Skill>>(listOf())

    fun addExperience(skill: Skills, experience : Int) = flow {
        val expEvent = ModifyExperienceEvent(skills[skill.statId], experience, experience)
        emit(expEvent)
        if(!expEvent.ignored) {
            if(expEvent.source.experience >= maxExperience) {
                return@flow
            }
            expEvent.source.experience += expEvent.experience
            skillState.value = listOf(expEvent.source)
            if(expEvent.source.experience >= maxExperience) {
                emit(MaxExperienceEvent(expEvent.source))
                return@flow
            }
            val lvl = ExperienceMath.levelForXp(expEvent.source.experience.toDouble())
            if(lvl > expEvent.source.staticLevel) {
                val levelUpEvent = LevelUpEvent(expEvent.source, expEvent.source.staticLevel, lvl)
                emit(levelUpEvent)
                if(!levelUpEvent.ignored) {
                    levelUpEvent.source.staticLevel = levelUpEvent.newLevel
                    if (levelUpEvent.restoreLevel) {
                        levelUpEvent.source.level = levelUpEvent.source.staticLevel
                    }
                }
            }
        }
    }

    fun invalidateSkills() {
        skillState.value = listOf(*skills)
    }

    fun setSkillLevel(skill : Skills, level : Int) {
        addExperience(skill, ExperienceMath.xpForLevel(level))
            .launchIn(CoroutineScope(Dispatchers.Unconfined))
    }

    fun setSkillExperience(skill: Skills, experience : Int) {
        addExperience(skill, experience)
            .launchIn(CoroutineScope(Dispatchers.Unconfined))
    }

    override fun toString(): String {
        return get<Gson>().toJson(skills)
    }
}