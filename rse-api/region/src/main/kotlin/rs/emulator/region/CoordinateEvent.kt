package rs.emulator.region

import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author javatar
 */

interface CoordinateEvent<C : Coordinate, S> {

    val source: S
    val oldCoordinate: C
    val newCoordinate: C

}