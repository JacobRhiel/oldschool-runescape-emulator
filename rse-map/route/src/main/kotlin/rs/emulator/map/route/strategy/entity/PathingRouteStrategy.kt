package rs.emulator.map.route.strategy.entity

import rs.emulator.map.MapGrid
import rs.emulator.map.route.strategy.RouteStrategy
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author Chk
 */
class PathingRouteStrategy(val map: MapGrid) : RouteStrategy()
{
    
    override fun find(srcCoordinate: Coordinate, destCoordinate: Coordinate, entitySize: Int, createAlternativeRoutes: Boolean): String
    {

        return fetchCollisionData(GRAPH_SIZE, map, WorldCoordinate(3222, 3222))
        
    }

}