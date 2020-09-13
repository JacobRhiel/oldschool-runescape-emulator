package rs.emulator.region.zones

import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author javatar
 */

interface ZoneBounds {

    fun intersects(coordinate: Coordinate) : Boolean

}