package rs.emulator.map.grid.tile

import rs.emulator.cache.definition.region.landscape.LandscapeLoc

/**
 *
 * @author Chk
 */
class GridTile(val x: Int,
               val z: Int,
               val plane: Int,
               val locs: MutableList<LandscapeLoc> = mutableListOf()
)
{

    val hash = ((x and 0x7FFF) or ((z and 0x7FFF) shl 15) or (plane shl 30))

}