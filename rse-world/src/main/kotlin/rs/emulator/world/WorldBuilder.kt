package rs.emulator.world

/**
 *
 * @author Chk
 */
class WorldBuilder
{

    private lateinit var origin: WorldOrigin

    private var members: Boolean = false

    private lateinit var activity: WorldActivity

    fun setOrigin(origin: WorldOrigin) : WorldBuilder = this.apply { this.origin = origin }

    fun setMembers(members: Boolean) : WorldBuilder = this.apply { this.members = members }

    fun setActivity(activity: WorldActivity) : WorldBuilder = this.apply { this.activity = activity }

    fun build() : World = World(members, activity, origin)

}