package rs.emulator.regions.zones

import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author javatar
 */

interface ZoneBounds {

    fun intersects(coordinate: Coordinate) : Boolean

}