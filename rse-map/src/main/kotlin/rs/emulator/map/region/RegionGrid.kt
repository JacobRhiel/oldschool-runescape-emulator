package rs.emulator.map.region

import com.google.common.collect.ArrayListMultimap
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.model.world.map.collision.add
import rs.emulator.cache.definition.definition
import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinition
import rs.emulator.definitions.entity.loc.LocDefinition
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.chunk.ChunkGrid
import rs.emulator.region.RegionCoordinate
import rs.emulator.utilities.koin.get

/**
 *
 * @author Chk
 */
class RegionGrid(val id: Int)
    : AreaGrid(width = 64, height = 64)
{

    private val mapScapeDefinition: MapScapeDefinition = definition().find(id)

    private val landscapeDefinition: LandscapeDefinition = definition().find(id, XteaKeyService.get(id))

    private val collisions: Collisions = get()

    private val chunks = mutableMapOf<Int, ChunkGrid>()

    private val tiles = mutableMapOf<Int, GridTile>()

    val regionStartTile = RegionCoordinate(((id shr 8) and 0xFF) shl 6, (id and 0xFF) shl 6, 0)

    //		this.baseX = ((id >> 8) & 0xFF) << 6; // local coords are in bottom 6 bits (64*64)
    //		this.baseY = (id & 0xFF) << 6;

    fun fetchChunkGrid(id: Int) = chunks.computeIfAbsent(id) { ChunkGrid(this) }

    fun fetchChunkGrid(x: Int, z: Int, height: Int) = chunks.computeIfAbsent((x shr z) and height) { ChunkGrid(this) }

    fun fetchChunks() = chunks.toMap()

    override fun constructGrid()
    {

        val plottedX = ArrayListMultimap.create<Int, Int>()

        val plottedZ = ArrayListMultimap.create<Int, Int>()

        for(plane in 0 until levels)
        {

            for(rx in 0 until width)
            {

                for(ry in 0 until height)
                {

                    val worldCoordinate = regionStartTile.add(rx, ry).toWorld()

                    val landscapeTile = landscapeDefinition.tiles[plane][rx][ry]

                    if(landscapeTile != null)
                    {

                        tiles.computeIfAbsent(worldCoordinate.as30BitInteger) {

                            //println("hash: " + worldCoordinate.as30BitInteger)

                            //println("type: ${tile.type}")

                            //  println("" + tile.localX + ", " + tile.localZ)

                            GridTile(landscapeTile.localX, landscapeTile.localZ, landscapeTile.plane, landscapeTile.types, landscapeTile.orientation)

                        }

                    }

                    val mapTileSettings = mapScapeDefinition.tiles[plane][rx][ry]?.settings

                   // println("coord: " + worldCoordinate.x + ", " + worldCoordinate.z + ", hash: " + worldCoordinate.as30BitInteger)

                    val gridTile = tiles[worldCoordinate.as30BitInteger]

                   // println("grid tile orientation: " + gridTile?.orientation)

                    //println("grid tile type: " + gridTile?.type)

                    //println("flag: " + gridTile?.types?.toTypedArray()?.contentDeepToString())

                   // collisions.add(3223, 3217, 0, 256)

                    val mask = 256

                    if(landscapeTile != null)
                    {

                        val loc: LocDefinition = definition().find(landscapeTile.id)

                        if(landscapeTile.types.any { it > 9 })
                        {

                            var mask = 256

                            if(loc.solid)
                                mask = mask or 0x20000

                            if(loc.clipMask != 0)
                                collisions.add(worldCoordinate.x, worldCoordinate.z, worldCoordinate.plane, mask)

                        }

                        //collisions.add(worldCoordinate.x, worldCoordinate.z, worldCoordinate.plane, flag)

                        //println("map settings: $mapTileSettings")

                        mapTileSettings?.toInt()
                                ?.let { collisions.add(worldCoordinate.x, worldCoordinate.z, worldCoordinate.plane, it) }
                    }
                }

            }

            /*for (x in 0 until width)
                if (x % 8 == 0)
                    plottedX.put(plane, if(x == 64) 63 else x)

            for (z in height downTo 0)
                if (z % 8 == 0)
                    plottedZ.put(plane, if(z == 64) 63 else z)

            plottedX[plane].forEach { x ->

                plottedZ[plane].forEach { z ->

                    val chunkGrid = fetchChunkGrid(x, z, plane)

                    chunkGrid.constructGrid(x, z, plane, landscapeDefinition)

                }

            }*/

        }

    }

}