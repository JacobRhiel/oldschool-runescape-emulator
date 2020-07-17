package rs.emulator.entity

import rs.emulator.region.WorldCoordinate

interface IEntity {

    val coordinate: WorldCoordinate
    val lastCoordinate: WorldCoordinate

}