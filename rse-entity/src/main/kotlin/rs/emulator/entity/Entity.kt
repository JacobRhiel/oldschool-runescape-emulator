package rs.emulator.entity

import rs.dusk.engine.model.entity.Size
import rs.emulator.region.WorldCoordinate

abstract class Entity(override val coordinate: WorldCoordinate = WorldCoordinate(x = 3222, z = 3218)) : IEntity
{

    open val size: Size = Size(1, 1)

    override val lastCoordinate: WorldCoordinate = WorldCoordinate(coordinate.x, coordinate.y, coordinate.plane)

}