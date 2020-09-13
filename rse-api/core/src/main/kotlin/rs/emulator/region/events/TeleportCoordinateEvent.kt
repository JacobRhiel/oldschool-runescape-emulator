package rs.emulator.region.events

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.region.CoordinateEvent
import rs.emulator.region.WorldCoordinate

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class TeleportCoordinateEvent<S>(
    override val source: S,
    override val oldCoordinate: WorldCoordinate,
    override val newCoordinate: WorldCoordinate = oldCoordinate
) : CoordinateEvent<WorldCoordinate, S>