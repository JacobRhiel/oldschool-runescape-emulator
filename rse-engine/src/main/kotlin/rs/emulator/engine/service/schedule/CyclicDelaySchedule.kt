package rs.emulator.engine.service.schedule

import com.google.common.util.concurrent.AbstractScheduledService
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chk
 */
@Suppress("UnstableApiUsage")
class CyclicDelaySchedule : AbstractScheduledService.CustomScheduler()
{

    private val cycleDuration = 600

    var lastCycle = 0L

    val remaining: Long
        get() = cycleDuration - (System.currentTimeMillis() - lastCycle)

    override fun getNextSchedule(): Schedule = Schedule(remaining, TimeUnit.MILLISECONDS)

}