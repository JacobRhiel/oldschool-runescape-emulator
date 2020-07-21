package rs.emulator.map.region.chunk

import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.RegionGrid

/**
 *
 * @author Chk
 */
class ChunkGrid(val region: RegionGrid)
    : AreaGrid(width = 8, height = 8)
{

    val tiles = mutableMapOf<Int, GridTile>()

    fun constructGrid(startX: Int, startZ: Int, plane: Int, landscapeDefinition: LandscapeDefinition)
    {

        val zOffset = if(startZ == 0) startZ else (startZ - 7)

        for(rx in startX until (startX + 7))
        {

            for(rz in startZ downTo zOffset)
            {

                val regionZ = 63 - rz

                val tile = landscapeDefinition.tiles[rx][regionZ][plane]

                if(tile != null)
                    tiles.computeIfAbsent((rx shr 3) + (regionZ shr 3) and plane) { GridTile(rx, regionZ, plane, tile.types, tile.orientation) }
                else
                    tiles.computeIfAbsent((rx shr 3) + (regionZ shr 3) and plane) { GridTile(rx, regionZ, plane) }

            }

        }

    }

}