package rs.dusk.engine.path.target

import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.path.TargetStrategy
import rs.emulator.region.coordinate.Coordinate

/**
 * Checks if within reachable range of a tile
 * e.g floor item on a tile or table
 * Note: Doesn't check if blocked
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class PointTargetStrategy(
    override val tile: Coordinate,
    override val size: Size = Size.TILE
) : TargetStrategy {

    override fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean {
        if (tile.x + size.width <= currentX || tile.x >= currentX + size.width) {
            return false
        }
        return currentY < tile.z + size.height && size.height + currentY > tile.z
    }
}