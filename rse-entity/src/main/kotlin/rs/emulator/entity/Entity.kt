package rs.emulator.entity

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import rs.dusk.engine.model.entity.Size
import rs.emulator.region.*

@ExperimentalCoroutinesApi
abstract class Entity(final override val coordinateState: MutableStateFlow<WorldCoordinate> = MutableStateFlow(WorldCoordinate(x = 3222, z = 3218))) : IEntity
{

    override val coordinate: WorldCoordinate by coordinateState

    open val size: Size = Size(1, 1)

    override val lastCoordinate: WorldCoordinate = WorldCoordinate(coordinate.x, coordinate.y, coordinate.plane)

}