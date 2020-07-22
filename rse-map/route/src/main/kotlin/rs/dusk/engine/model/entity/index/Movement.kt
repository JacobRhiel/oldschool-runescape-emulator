package rs.dusk.engine.model.entity.index

import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.path.PathFinder
import rs.dusk.engine.path.PathResult
import rs.dusk.engine.path.TraversalStrategy
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.IActor
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.utilities.koin.get
import java.util.*

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 26, 2020
 */

typealias Steps = LinkedList<Direction>

data class Movement(
    var lastCoordinate: Coordinate = Coordinate.EMPTY,
    var delta: Coordinate = Coordinate.EMPTY,
    var walkStep: Direction = Direction.NONE,
    var runStep: Direction = Direction.NONE,
    val steps: LinkedList<Direction> = LinkedList(),
    val directions: Array<Array<Direction?>> = Array(BreadthFirstSearch.GRAPH_SIZE) {
        Array<Direction?>(BreadthFirstSearch.GRAPH_SIZE) { null }
    },
    val distances: Array<IntArray> = Array(BreadthFirstSearch.GRAPH_SIZE) { IntArray(BreadthFirstSearch.GRAPH_SIZE) },
    val calc: Queue<Coordinate> = LinkedList(),
    var frozen: Boolean = false,
    var running: Boolean = false
)
{

    lateinit var traversal: TraversalStrategy

    fun clear() {
        steps.clear()
        reset()
    }

    fun reset() {
        delta = Coordinate.EMPTY
        walkStep = Direction.NONE
        runStep = Direction.NONE
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movement

        if (lastCoordinate != other.lastCoordinate) return false
        if (delta != other.delta) return false
        if (walkStep != other.walkStep) return false
        if (runStep != other.runStep) return false
        if (steps != other.steps) return false
        if (!directions.contentDeepEquals(other.directions)) return false
        if (!distances.contentDeepEquals(other.distances)) return false
        if (calc != other.calc) return false
        if (frozen != other.frozen) return false
        if (running != other.running) return false
        if (traversal != other.traversal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lastCoordinate.hashCode()
        result = 31 * result + delta.hashCode()
        result = 31 * result + walkStep.hashCode()
        result = 31 * result + runStep.hashCode()
        result = 31 * result + steps.hashCode()
        result = 31 * result + directions.contentDeepHashCode()
        result = 31 * result + distances.contentDeepHashCode()
        result = 31 * result + calc.hashCode()
        result = 31 * result + frozen.hashCode()
        result = 31 * result + running.hashCode()
        result = 31 * result + traversal.hashCode()
        return result
    }
}

/*
fun Character.walkTo(target: Any, action: (PathResult) -> Unit) = action(ActionType.Movement) {
    try {
        val result = calcPath(this@walkTo, target)
        if (result is PathResult.Failure) {
            action(result)
        } else {
            while (delay() && awaitInterfaces()) {
                if (movement.steps.isEmpty()) {
                    break
                }
            }
            action(result)
        }
    } finally {
        movement.clear()
    }
}*/
