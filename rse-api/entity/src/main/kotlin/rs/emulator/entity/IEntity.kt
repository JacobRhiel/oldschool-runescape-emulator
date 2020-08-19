package rs.emulator.entity

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.region.EntityCoordinateState
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate

@ExperimentalCoroutinesApi
interface IEntity {

    val coordinateState: EntityCoordinateState
    val coordinate: Coordinate
    val lastCoordinate: WorldCoordinate

}
