package rs.emulator.world

/**
 *
 * @author Chk
 */
data class World(
    private val members: Boolean,
    private val activity: WorldActivity,
    private val origin: WorldOrigin
)
{

    override fun toString(): String = "[World-'insert_build'] -> attrs[members: $members, activity: $activity, origin: $origin]"

}