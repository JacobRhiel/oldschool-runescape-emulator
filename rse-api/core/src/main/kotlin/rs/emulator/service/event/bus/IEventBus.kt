package rs.emulator.service.event.bus

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.ofType
import rs.emulator.service.event.IEvent

/**
 *
 * @author javatar
 */

interface IEventBus<Event : IEvent> {

    val eventProcessor : PublishProcessor<Event>

    fun post(event : Event)

    fun observeEvents() : Flowable<Event>

}

inline fun <reified T : IEvent> IEventBus<T>.observeEvent() : Flowable<T> {
    return observeEvents().ofType()
}