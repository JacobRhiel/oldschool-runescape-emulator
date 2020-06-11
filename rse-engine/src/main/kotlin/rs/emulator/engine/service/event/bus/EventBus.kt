package rs.emulator.engine.service.event.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Subscription
import rs.emulator.engine.service.event.Event

/**
 *
 * @author Chk
 */
class EventBus
{

    val publisher = PublishSubject.create<Event>()

    inline fun <reified T : Event> observe(): Observable<T> = publisher.ofType(T::class.java)

    fun post(event: Event) = publisher.onNext(event)


}