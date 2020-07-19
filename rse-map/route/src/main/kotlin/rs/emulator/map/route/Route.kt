package rs.emulator.map.route

import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile

/**
 *
 * @author Chk
 */
abstract class Route
{

    private val tileNodes = mutableMapOf<Int, GridTile>()

    fun calculateRoute(area: AreaGrid, sourceTile: GridTile, destTile: GridTile)
    {



    }

}