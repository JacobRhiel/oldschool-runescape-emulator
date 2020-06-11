@file:Suppress("UnstableApiUsage")

package rs.emulator.engine.service

import com.google.common.util.concurrent.AbstractScheduledService
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.engine.service.event.*
import rs.emulator.engine.service.schedule.CyclicDelaySchedule
import rs.emulator.utilities.logger.warn
import java.util.concurrent.*

/**
 *
 * @author Chk
 */
class CyclicEngineService : KoinComponent, AbstractScheduledService()
{

    private val eventList = mutableListOf<Event>()

    private val storedExecutionTimes = Object2LongOpenHashMap<Event>()

    override fun scheduler() = inject<CyclicDelaySchedule>().value

    override fun runOneIteration()
    {

        scheduler().lastCycle = System.currentTimeMillis()

        val iterator = eventList.listIterator()

        while(iterator.hasNext())
        {

            val event = iterator.next()

            val completableFuture = CompletableFuture.supplyAsync(event::execute)

            val remainingMilliseconds = scheduler().remaining

            var successful = true

            try
            {

                val start = System.currentTimeMillis()

                successful = completableFuture.get(remainingMilliseconds, TimeUnit.MILLISECONDS)

                completableFuture.join()

                val end = System.currentTimeMillis()

                //todo: verify no event is the same.
                storedExecutionTimes[event] = (end - start)

            }
            catch (e: TimeoutException)
            {

                println("error")

            }

            if(!successful)
                warn("Unsuccessful event execution $event")

        }

    }

    fun schedule(event: Event)
    {

        if(eventList.contains(event))
            warn("[Event: $event has already been scheduled.")
        else
            eventList.add(event)

    }

}