package rs.emulator.map.region.chunk

import org.koin.core.KoinComponent
import org.koin.core.get
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.model.world.map.collision.add
import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.RegionGrid
import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate

/**
 *
 * @author Chk
 */
class ChunkGrid(val region: RegionGrid)
    : AreaGrid(width = 8, height = 8), KoinComponent
{

    private val collisions: Collisions = get()

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

                val coordinate = if(tile == null) RegionCoordinate(rx shr 3, rz shr 3, plane) else RegionCoordinate(tile.localX, tile.localZ, tile.plane)

                val gridTile = tiles.computeIfAbsent(coordinate.toWorld().as30BitInteger) {
                    GridTile(
                        rx,
                        regionZ,
                        plane,
                        tile?.types ?: mutableListOf(),
                        tile?.orientation ?: 0
                    )
                }

                gridTile.types.forEach { type -> collisions.add(gridTile.x, gridTile.z, gridTile.plane, type) }

            }

        }

    }

}