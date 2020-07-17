package rs.emulator.world

import org.koin.core.KoinComponent
import rs.emulator.database.annotations.NoArg
import rs.emulator.database.entry.Entry
import rs.emulator.map.MapGrid
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 *
 * @author Chk
 */
@NoArg
@Entity
@Table(name = "worlds")
open class World(
    @Id @Column(name = "identifier", unique = true, nullable = false) var identifier: Int,
    @Column(name = "members") private val members: Boolean,
    @Column(name = "access") private val access: WorldAccess,
    @Column(name = "activity") private val activity: WorldActivity,
    @Column(name = "origin") private val origin: WorldOrigin,
    val mapGrid: MapGrid
) : KoinComponent, Entry
{

    override fun toString(): String = "[World-'insert_build'] -> attrs[members: $members, activity: $activity, origin: $origin]"

    companion object
    {

        fun newBuilder() = WorldBuilder()

    }

}