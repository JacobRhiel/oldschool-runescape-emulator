package rs.emulator.regions.zones.bounds

import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.regions.zones.ZoneBounds

class CircularArea(private val centerTile: Coordinate, private val radius: Int) : ZoneBounds
{

	constructor(x: Int, y: Int, z: Int, radius : Int) : this(WorldCoordinate(x, y, z), radius)

	override fun intersects(coordinate: Coordinate) : Boolean
	{

		val radiusSq = radius * radius

		val xOffset = coordinate.x - centerTile.x
		val yOffset = coordinate.y - centerTile.y

		return (xOffset * xOffset) + (yOffset * yOffset) < radiusSq

	}

}