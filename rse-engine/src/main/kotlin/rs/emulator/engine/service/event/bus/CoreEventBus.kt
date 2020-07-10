package rs.emulator.engine.service.event.bus

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import rs.emulator.service.event.bus.EventBusFactory
import rs.emulator.service.event.IEvent
import rs.emulator.service.event.bus.IEventBus

/**
 *
 * @author Chk
 */
object CoreEventBus : EventBusFactory<IEvent>, IEventBus<IEvent>
{

    override val eventProcessor: PublishProcessor<IEvent> = PublishProcessor.create()

    override fun createEventBus(): IEventBus<IEvent> {
        return this
    }

    override fun post(event: IEvent) {
        eventProcessor.onNext(event)
    }

    override fun observeEvents(): Flowable<IEvent> {
        return eventProcessor.serialize()
    }


}