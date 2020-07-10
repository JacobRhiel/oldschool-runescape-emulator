@file:Suppress("UnstableApiUsage")

package rs.emulator.engine.service

import com.google.common.util.concurrent.AbstractScheduledService
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toFlowable
import org.koin.core.KoinComponent
import org.koin.core.inject
import rs.emulator.engine.service.event.bus.CoreEventBus
import rs.emulator.engine.service.schedule.CyclicDelaySchedule
import rs.emulator.plugins.RSPluginManager
import rs.emulator.service.event.bus.EventBusFactory
import rs.emulator.service.event.IEvent
import java.util.concurrent.CompletableFuture

/**
 *
 * @author Chk
 */
class CyclicEngineService : KoinComponent, AbstractScheduledService() {

    private val staticEvents = mutableListOf<IEvent>()

    override fun scheduler() = inject<CyclicDelaySchedule>().value

    override fun runOneIteration() {
        scheduler().lastCycle = System.currentTimeMillis()
        Flowable.concat(
            listOf(
                staticEvents.toFlowable(),
                CoreEventBus.observeEvents(),
                Flowable.concat(RSPluginManager.getExtensions(EventBusFactory::class.java)
                    .map { it.createEventBus().observeEvents() })
            )
        ).map { CompletableFuture.supplyAsync(it::execute) }.concatMap { Flowable.fromFuture(it) }.subscribe()
    }


    fun schedule(event: IEvent, staticEvent: Boolean = false) {

        if (!staticEvent) {
            CoreEventBus.post(event)
        } else {
            staticEvents.add(event)
        }

    }

}