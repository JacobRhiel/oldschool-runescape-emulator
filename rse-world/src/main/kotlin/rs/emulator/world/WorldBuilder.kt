package rs.emulator.world

import rs.emulator.map.MapGrid

/**
 *
 * @author Chk
 */
class WorldBuilder
{

    private lateinit var origin: WorldOrigin

    private var members: Boolean = false

    private lateinit var activity: WorldActivity

    private lateinit var access: WorldAccess

    fun setOrigin(origin: WorldOrigin) : WorldBuilder = this.apply { this.origin = origin }

    fun setMembers(members: Boolean) : WorldBuilder = this.apply { this.members = members }

    fun setActivity(activity: WorldActivity) : WorldBuilder = this.apply { this.activity = activity }

    fun setAccess(access: WorldAccess) : WorldBuilder = this.apply { this.access = access }

    fun build() : World = World(0, members, access, activity, origin, MapGrid())

}