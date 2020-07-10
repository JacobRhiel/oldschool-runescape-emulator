package rs.emulator.service.event.bus

import org.pf4j.ExtensionPoint
import rs.emulator.service.event.IEvent

/**
 *
 * @author javatar
 */

interface EventBusFactory<T : IEvent> : ExtensionPoint {

    fun createEventBus() : IEventBus<T>

}