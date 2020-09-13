package rs.emulator.entity

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.dusk.engine.model.entity.Size
import rs.emulator.region.*

@ExperimentalCoroutinesApi
abstract class Entity(final override val coordinateState: EntityCoordinateState = EntityCoordinateState(WorldCoordinate(x = 3222, z = 3218))) : IEntity
{

    override val coordinate: WorldCoordinate by coordinateState

    open val size: Size = Size(1, 1)

    override val lastCoordinate: WorldCoordinate = WorldCoordinate(coordinate.x, coordinate.y, coordinate.plane)

}