package rs.emulator.entity

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import rs.emulator.region.WorldCoordinate

@ExperimentalCoroutinesApi
interface IEntity {

    val coordinateState: MutableStateFlow<WorldCoordinate>
    val coordinate: WorldCoordinate
    val lastCoordinate: WorldCoordinate

}
