package rs.dusk.engine.model.entity

import rs.emulator.region.RegionCoordinate
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.chunk.ChunkCoordinate

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
enum class Direction(deltaX: Int, deltaY: Int)
{

    SOUTH_WEST(-1, -1),
    SOUTH(0, -1),
    SOUTH_EAST(1, -1),
    WEST(-1, 0),
    EAST(1, 0),
    NORTH_WEST(-1, 1),
    NORTH(0, 1),
    NORTH_EAST(1, 1),
    NONE(0, 0);

    val delta = RegionCoordinate(deltaX, deltaY)

    fun isDiagonal() = isHorizontal() && isVertical()

    fun isCardinal(): Boolean = delta.x == 0 || delta.z == 0

    fun isHorizontal() = delta.x != 0

    fun isVertical() = delta.z != 0

    fun vertical(): Direction
    {
        return when (delta.z)
        {
            1 -> NORTH
            -1 -> SOUTH
            else -> NONE
        }
    }

    fun horizontal(): Direction
    {
        return when (delta.x)
        {
            1 -> EAST
            -1 -> WEST
            else -> NONE
        }
    }

    fun inverse(): Direction
    {
        return when (this)
        {
            NORTH_WEST -> SOUTH_EAST
            NORTH -> SOUTH
            NORTH_EAST -> SOUTH_WEST
            EAST -> WEST
            SOUTH_EAST -> NORTH_WEST
            SOUTH -> NORTH
            SOUTH_WEST -> NORTH_EAST
            WEST -> EAST
            NONE -> NONE
        }
    }

    companion object
    {

        val size = values().size

        val cardinal = values().filter { it.isCardinal() && it.delta.x != it.delta.z }

        val ordinal = values().filter { it.isDiagonal() }

        val all = values().copyOfRange(0, size - 1)

        val clockwise = arrayOf(
            NORTH,
            NORTH_EAST,
            EAST,
            SOUTH_EAST,
            SOUTH,
            SOUTH_WEST,
            WEST,
            NORTH_WEST
        )

    }

}