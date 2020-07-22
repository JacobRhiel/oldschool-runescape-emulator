package rs.dusk.engine.path

import rs.dusk.engine.model.entity.Size
import rs.emulator.region.coordinate.Coordinate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
interface TargetStrategy
{

    val tile: Coordinate

    val size: Size

    fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean

    fun reached(tile: Coordinate, size: Size) = reached(tile.x, tile.z, tile.plane, size)

}