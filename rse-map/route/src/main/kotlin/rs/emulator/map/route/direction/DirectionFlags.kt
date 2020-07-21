package rs.emulator.map.route.direction

/**
 *
 * @author Chk
 */
enum class DirectionFlags(val x: Int,
                          val z: Int
)
{

    NORTH(0, 1),

    NORTH_EAST(1, 1),

    NORTH_WEST(-1, 1),

    EAST(1, 0),

    SOUTH(0, -1),

    SOUTH_EAST(1, -1),

    SOUTH_WEST(-1, -1),

    WEST(-1, 0)

    ;

    companion object
    {

        fun fetchCardinal(dx: Int, dz: Int): DirectionFlags
        {
            when
            {
                dx > 0 -> return if(dz >= 0)
                    when
                    {
                        dx == dz -> NORTH_EAST
                        dx > dz -> EAST
                        else -> NORTH
                    }
                else
                {
                    when
                    {
                        dx == -dz -> SOUTH_EAST
                        dx > -dz -> EAST
                        else -> SOUTH
                    }
                }
                -dx > 0 -> return if(dz >= 0)
                    when
                    {
                        -dx == dz -> NORTH_WEST
                        -dx > dz -> WEST
                        else -> NORTH
                    }
                else
                {
                    when
                    {
                        -dx == -dz -> SOUTH_WEST
                        -dx > -dz -> WEST
                        else -> SOUTH
                    }
                }
                dz > 0 -> return NORTH
                -dz > 0 -> return SOUTH
                else -> return SOUTH
            }
        }

    }

}