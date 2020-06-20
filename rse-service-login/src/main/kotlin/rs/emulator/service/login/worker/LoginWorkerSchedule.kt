package rs.emulator.service.login.worker

import com.google.common.util.concurrent.AbstractScheduledService
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chk
 */
class LoginWorkerSchedule : AbstractScheduledService.CustomScheduler()
{

    private val cycleDuration = Duration.ofSeconds(1).toMillis()

    var lastCycle = 0L

    val remaining: Long
        get() = cycleDuration - (System.currentTimeMillis() - lastCycle)

    override fun getNextSchedule(): Schedule = Schedule(remaining, TimeUnit.MILLISECONDS)

}