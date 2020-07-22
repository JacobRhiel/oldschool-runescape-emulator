package rs.dusk.engine.path

import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.entity.index.Movement
import rs.emulator.region.coordinate.Coordinate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
interface Finder {
    /**
     * Calculates a route from [tile] to [strategy.target]
     * taking into account movement allowed by [traversal]
     * appending the individual steps to [movement.steps].
     * @return Success, Partial (movement but not reached target), Failure
     */
    fun find(
        tile: Coordinate,
        size: Size,
        movement: Movement,
        strategy: TargetStrategy,
        traversal: TraversalStrategy
    ): PathResult
}