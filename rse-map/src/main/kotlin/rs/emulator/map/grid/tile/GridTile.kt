package rs.emulator.map.grid.tile

/**
 *
 * @author Chk
 */
class GridTile(val x: Int,
               val z: Int,
               val plane: Int,
               val types: MutableList<Int> = mutableListOf(),
               val orientation: Int = 0
)
{

    val hash = ((x and 0x7FFF) or ((z and 0x7FFF) shl 15) or (plane shl 30))

}