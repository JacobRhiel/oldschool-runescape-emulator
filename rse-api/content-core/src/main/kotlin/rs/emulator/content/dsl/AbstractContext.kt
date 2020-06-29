package rs.emulator.content.dsl

import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 *
 * @author javatar
 */

abstract class AbstractContext<T : AbstractContext<T>> {

    infix fun T.after(time : Long) : TimeInfo {
        return TimeInfo(time)
    }

    suspend infix fun TimeInfo.seconds(logic : suspend T.() -> Unit) {
        this.time = TimeUnit.SECONDS.toMillis(this.time)
        delayLogic(this, logic)
    }

    suspend infix fun TimeInfo.minutes(logic : suspend T.() -> Unit) {
        this.time = TimeUnit.MINUTES.toMillis(this.time)
        delayLogic(this, logic)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun delayLogic(info : TimeInfo, logic : suspend T.() -> Unit) {
        delay(info.time)
        logic(this as T)
    }
    data class TimeInfo(var time : Long)

}