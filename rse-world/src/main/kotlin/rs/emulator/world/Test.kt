package rs.emulator.world

/**
 *
 * @author Chk
 */
object Test
{

    @JvmStatic
    fun main(args: Array<String>)
    {

        val world = WorldBuilder().setMembers(true).setOrigin(WorldOrigin.UNITED_STATES).setActivity(WorldActivity.NONE).build()

        println(world)

    }

}