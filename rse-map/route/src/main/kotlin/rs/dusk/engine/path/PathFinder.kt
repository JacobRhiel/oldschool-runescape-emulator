package rs.dusk.engine.path

import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.path.find.AxisAlignment
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.dusk.engine.path.find.DirectSearch

/**
 * Determines the correct strategy to use to reach a target [Entity] or [Tile]
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 21, 2020
 */
data class PathFinder(
    val collisions: Collisions,
    val aa: AxisAlignment,
    val ds: DirectSearch,
    val bfs: BreadthFirstSearch
) {

    /*fun find(source: IActor, tile: Coordinate): PathResult {
        val strategy = TileTargetStrategy(tile = tile)
        if(strategy.reached(source.tile, source.size)) {
            return PathResult.Success.Complete(source.tile)
        }
        val finder = getFinder(source)
        return finder.find(source.tile, source.size, source.movement, strategy, source.movement.traversal)
    }

    fun find(source: IActor, target: IEntity): PathResult {
        val strategy = getStrategy(target)
        if(strategy.reached(source.tile, source.size)) {
            return PathResult.Success.Complete(source.tile)
        }
        val finder = getFinder(source)
        return finder.find(source.tile, source.size, source.movement, strategy, source.movement.traversal)
    }

    fun getFinder(source: IActor): Finder {
        return if (source is IPlayer) {
            bfs
        } else {
            aa
        }
    }

    fun getStrategy(target: IEntity) = when (target) {
        is IObject -> when (target.type) {
            in 0..2, 9 -> WallTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                rotation = target.rotation,
                type = target.type
            )
            in 3..8 -> DecorationTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                rotation = target.rotation,
                type = target.type
            )
            10, 11, 22 -> RectangleTargetStrategy(
                collisions,
                tile = target.tile,
                size = target.size,
                blockFlag = target.def.blockFlag
            )
            else -> TileTargetStrategy(tile = target.tile)
        }
        *//*is FloorItem -> PointTargetStrategy(
            tile = target.tile,
            size = target.size
        )*//*
    }*/
}