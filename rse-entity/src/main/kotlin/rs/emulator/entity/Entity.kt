package rs.emulator.entity

import rs.emulator.region.WorldCoordinate

abstract class Entity(val coordinate: WorldCoordinate = WorldCoordinate(x = 3222, z = 3218)) : IEntity
{

    val lastCoordinate: WorldCoordinate = WorldCoordinate(coordinate.x, coordinate.z, coordinate.plane)

}