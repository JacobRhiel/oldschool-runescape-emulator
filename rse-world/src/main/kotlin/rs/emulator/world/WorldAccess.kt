package rs.emulator.world

/**
 *
 * @author Chk
 */
enum class WorldAccess(val flag: Int)
{

    DEVELOPMENT(0),

    TESTING(1),

    CLOSED_BETA(2),

    OPEN_BETA(3),

    LIVE(4)

    ;

    companion object
    {

        fun fetchAccessForFlag(flag: Int) = values().firstOrNull { it.flag == flag }

    }


}