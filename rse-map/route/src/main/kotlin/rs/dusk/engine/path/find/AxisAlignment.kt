package rs.dusk.engine.path.find

import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.entity.Size
import rs.dusk.engine.model.entity.index.Movement
import rs.dusk.engine.path.Finder
import rs.dusk.engine.path.PathResult
import rs.dusk.engine.path.TargetStrategy
import rs.dusk.engine.path.TraversalStrategy
import rs.emulator.region.coordinate.Coordinate

/**
 * Moves diagonally until aligned with target or blocked by obstacle then moves cardinally
 * Used by NPCs
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 31, 2020
 */
class AxisAlignment : Finder {

    override fun find(
        tile: Coordinate,
        size: Size,
        movement: Movement,
        strategy: TargetStrategy,
        traversal: TraversalStrategy
    ): PathResult {
        var delta = strategy.tile.delta(tile)
        var current = tile

        var reached = strategy.reached(current, size)
        while (!reached) {
            var direction = toDirection(delta)
            if (traversal.blocked(current, direction)) {
                direction = if (direction.isDiagonal()) {
                    if (!traversal.blocked(current, direction.horizontal())) {
                        direction.horizontal()
                    } else if (!traversal.blocked(current, direction.vertical())) {
                        direction.vertical()
                    } else {
                        break
                    }
                } else {
                    break
                }
            }
            if (direction == Direction.NONE) {
                break
            }
            delta = delta.minus(direction.delta)
            current = current.add(direction.delta)
            movement.steps.add(direction)
            reached = strategy.reached(current, size)
        }

        return when {
            reached -> PathResult.Success.Complete(current)
            current != tile -> PathResult.Success.Partial(current)
            else -> PathResult.Failure
        }
    }

    fun toDirection(delta: Coordinate) = when {
        delta.x > 0 -> when {
            delta.z > 0 -> Direction.NORTH_EAST
            delta.z < 0 -> Direction.SOUTH_EAST
            else -> Direction.EAST
        }
        delta.x < 0 -> when {
            delta.z > 0 -> Direction.NORTH_WEST
            delta.z < 0 -> Direction.SOUTH_WEST
            else -> Direction.WEST
        }
        else -> when {
            delta.z > 0 -> Direction.NORTH
            delta.z < 0 -> Direction.SOUTH
            else -> Direction.NONE
        }
    }
}