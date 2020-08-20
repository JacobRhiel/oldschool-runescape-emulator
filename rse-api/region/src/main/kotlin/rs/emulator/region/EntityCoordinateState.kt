package rs.emulator.region

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import rs.emulator.region.events.TeleportCoordinateEvent
import kotlin.reflect.KProperty

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
class EntityCoordinateState(val coordinate: WorldCoordinate, val events: ConflatedBroadcastChannel<CoordinateEvent<*, *>> = ConflatedBroadcastChannel()) {

    var isTeleporting: Boolean = false

    operator fun set(value: WorldCoordinate, event: CoordinateEvent<*, *>) {
        handleCoordinateEvent(event)
        coordinate.set(value)
    }

    operator fun set(x: Int, y: Int, plane: Int = coordinate.plane, event: CoordinateEvent<*, *>) {
        handleCoordinateEvent(event)
        coordinate.set(x, y)
    }

    private fun handleCoordinateEvent(event: CoordinateEvent<*, *>) {
        events.sendBlocking(event)
        if(event is TeleportCoordinateEvent) {
            isTeleporting = true
        }
    }

    fun set(value: WorldCoordinate) {
        coordinate.set(value)
    }

    operator fun getValue(any: Any?, property: KProperty<*>) : WorldCoordinate = coordinate
}