package rs.emulator.entity.actor.affects

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.IActor
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

/**
 * The persistent affects map has a value of [String], this [String] is used as the key in the database
 * @author javatar
 */

@ExperimentalCoroutinesApi
class AffectHandler<A : IActor> {

    val affects: MutableMap<Timer, String> = mutableMapOf()
    val affectsByName: MutableMap<String, Timer> = mutableMapOf()

    fun addIntervalAffect(
        name: String? = null,
        initialDelay: Long = 0L,
        period: Long,
        affect: IAffect<*, *>
    ) : () -> Unit {
        val timer = fixedRateTimer(name, false, initialDelay, period) {
            affect.applyAffect()
        }
        if (name != null) {
            affects[timer] = name
            affectsByName[name] = timer
        } else {
            affects[timer] = affect::class.java.simpleName
            affectsByName[affect::class.java.simpleName] = timer
        }
        return {
            timer.cancel()
            affects.remove(timer)
        }
    }

    fun <T : IActor> addDelayedAffect(delay: Long, affect: IAffect<T, A>) {
        timer(null, false, delay, 0) {
            affect.applyAffect()
            this.cancel()
        }
    }

    fun <T : IActor> doInstantAffect(affect: IAffect<T, A>) {
        affect.applyAffect()
    }

    fun findAffectByName(name: String) : Timer? {
        return affectsByName[name]
    }

    fun cancelAffectByName(name: String) {
        affectsByName[name]?.let {
            it.cancel()
            affects.remove(it)
            affectsByName.remove(name)
        }
    }

    fun cancelAffect(timer: Timer) = affects.keys.firstOrNull { it === timer }?.cancel()

    fun clearAffects() {
        affects.keys.forEach { it.cancel() }
        affects.clear()
    }

}