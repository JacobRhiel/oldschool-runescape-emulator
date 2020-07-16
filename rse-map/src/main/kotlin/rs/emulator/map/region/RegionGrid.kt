package rs.emulator.map.region

import com.google.common.collect.ArrayListMultimap
import io.reactivex.processors.PublishProcessor
import rs.emulator.cache.definition.definition
import rs.emulator.cache.definition.region.landscape.LandscapeDefinition
import rs.emulator.cache.definition.region.mapscape.MapScapeDefinition
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.grid.tile.GridTile
import rs.emulator.map.region.chunk.ChunkGrid

/**
 *
 * @author Chk
 */
class RegionGrid(val id: Int)
    : AreaGrid(width = 64, height = 64)
{

    private val mapScapeDefinition: MapScapeDefinition = definition().find(((id shr 8) * 64) shr 6)

    private val landscapeDefinition: LandscapeDefinition = definition().find(((id shr 8) * 64) shr 6, XteaKeyService.get(id))

    private val chunks = mutableMapOf<Int, ChunkGrid>()

    private val tiles = mutableMapOf<ChunkGrid, Map<Int, GridTile>>()

    val regionStartTile = GridTile(((id shr 8) and 0xFF) shl 6, (id and 0xFF) shl 6, 0)

    fun fetchChunkGrid(id: Int) = chunks.computeIfAbsent(id) { ChunkGrid(this) }

    fun fetchChunkGrid(x: Int, z: Int, height: Int) = chunks.computeIfAbsent((x shr z) and height) { ChunkGrid(this) }

    override fun constructGrid()
    {

        val plottedX = ArrayListMultimap.create<Int, Int>()

        val plottedZ = ArrayListMultimap.create<Int, Int>()

        for(plane in 0 until levels)
        {

            for (x in 0 until width)
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

            }

        }

    }

}