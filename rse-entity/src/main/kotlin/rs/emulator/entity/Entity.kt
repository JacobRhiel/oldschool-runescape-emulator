package rs.emulator.entity

import rs.emulator.region.WorldCoordinate

abstract class Entity(final override val coordinate: WorldCoordinate = WorldCoordinate(x = 3222, z = 3218)) : IEntity {

    override val lastCoordinate: WorldCoordinate = WorldCoordinate(coordinate.x, coordinate.z, coordinate.plane)

}