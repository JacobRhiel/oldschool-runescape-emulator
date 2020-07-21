package rs.emulator.map

import io.reactivex.processors.PublishProcessor
import rs.emulator.map.grid.AreaGrid
import rs.emulator.map.region.Region
import rs.emulator.map.region.RegionGrid

/**
 *
 * @author Chk
 */
class MapGrid : AreaGrid(width = 32768, height = 32768)
{

    private val processor = PublishProcessor.create<Region>()

    private val regions = mutableMapOf<Int, Region>()

    fun fetchRegion(id: Int): Region = regions.computeIfAbsent(id) { Region(RegionGrid(id).apply { constructGrid() }) }

    fun loadRegion(id: Int) : Region
    {

        val region = fetchRegion(id)

        regions.computeIfAbsent(id) { region }

        processor.offer(region)

        return region

    }

    fun fetchRegion(x: Int, z: Int): Region
    {

        val regionId = ((x shr 6) shl 8) or (z shr 6)

        println("region id: $regionId")

        return loadRegion(regionId)

    }

}