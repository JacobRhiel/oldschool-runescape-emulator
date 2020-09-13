package rs.emulator.region.zones.bounds

import rs.emulator.region.coordinate.Coordinate
import rs.emulator.region.zones.ZoneBounds
import java.awt.Polygon

class PolygonalArea(val plane: Int, tiles: ArrayList<Coordinate>) : ZoneBounds, Polygon() {

    init {
        xpoints = tiles.map { it.x }.toIntArray()
        ypoints = tiles.map { it.y }.toIntArray()
        npoints = tiles.size
    }

    override fun intersects(coordinate: Coordinate): Boolean {
        return intersects(coordinate.x.toDouble(), coordinate.y.toDouble(), 1.0, 1.0)
    }

}