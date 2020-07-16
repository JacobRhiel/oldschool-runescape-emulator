package rs.emulator.map.grid

/**
 *
 * @author Chk
 */
abstract class AreaGrid(
    val width: Int,
    val height: Int,
    val levels: Int = 4
)
{

    open fun constructGrid() { }

}