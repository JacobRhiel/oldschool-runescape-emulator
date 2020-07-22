package rs.dusk.engine.path.target

import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.path.TargetStrategy
import rs.emulator.region.coordinate.Coordinate

/**
 * Checks if on an exact tile
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class TileTargetStrategy(
    override val tile: Coordinate,
    override val size: Size = Size.TILE
) : TargetStrategy
{

    override fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean = tile.equals(currentX, currentY, plane)

}