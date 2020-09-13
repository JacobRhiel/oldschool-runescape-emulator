package rs.emulator.region.zones.bounds

import rs.emulator.region.coordinate.Coordinate
import rs.emulator.region.zones.ZoneBounds

class RectangularArea(val startX: Int, val startY: Int, val endX: Int, val endY: Int, private val plane: Int) :
    ZoneBounds {

    override fun intersects(coordinate: Coordinate): Boolean {
        return coordinate.x >= startX && coordinate.y >= startY && coordinate.x <= endX && coordinate.y <= endY && coordinate.plane == plane
    }

    companion object {

        fun create(startX: Int, startY: Int, width: Int, height: Int, plane: Int = 0) : RectangularArea {
            return RectangularArea(startX, startY, startX + width, startY + height, plane)
        }

    }

}