package rs.emulator.entity.actor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.entity.index.Movement
import rs.dusk.engine.path.*
import rs.dusk.engine.path.target.PointTargetStrategy
import rs.dusk.engine.path.target.RectangleTargetStrategy
import rs.dusk.engine.path.target.TileTargetStrategy
import rs.dusk.engine.path.traverse.SmallTraversal
import rs.emulator.entity.Entity
import rs.emulator.entity.action.queue.ActionQueue
import rs.emulator.map.region.Region
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.utilities.koin.get

@ExperimentalCoroutinesApi
abstract class Actor(var index: Int) : Entity(), IActor
{

    constructor(index: Int, coordinate: WorldCoordinate) : this(index)
    {
        this.coordinate.set(coordinate)
    }

    protected val pathFinder: PathFinder = get()

    var direction: Direction = Direction.SOUTH

    var lastRegion: WorldCoordinate? = coordinate

    val movement = Movement().apply {
        this.traversal = SmallTraversal(TraversalType.Land, false, pathFinder.collisions)
    }//todo move to only create on login.

    var targetCoordinate: Coordinate? = null

    abstract val searchPattern: Finder

    open val target: Entity? = null

    open val strategy: TargetStrategy
         get() = PointTargetStrategy(
             //pathFinder.collisions,
             tile = targetCoordinate ?: coordinate,
             size = target?.size ?: size
         )

    val actions = ActionQueue()

    //pathfinding
    fun find(tile: Coordinate): PathResult
    {
        val strategy = TileTargetStrategy(tile = tile)
        return if(strategy.reached(coordinate, size))
            PathResult.Success.Complete(coordinate)
        else
            searchPattern.find(coordinate, size, movement, strategy, movement.traversal)
    }

    fun find(target: Entity): PathResult
    {
        return if(strategy.reached(coordinate, size))
            PathResult.Success.Complete(coordinate)
        else
            searchPattern.find(coordinate, size, movement, strategy, movement.traversal)
    }

    fun calcPath(target: Any) = when (target)
    {
        is Coordinate -> find(target)
        is Entity -> find( target)
        else -> PathResult.Failure
    }

    override fun resetMovement() {
        movement.clear()
    }
}