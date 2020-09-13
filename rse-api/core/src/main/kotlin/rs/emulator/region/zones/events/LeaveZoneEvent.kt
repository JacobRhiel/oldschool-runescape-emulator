package rs.emulator.region.zones.events

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.IActor
import rs.emulator.region.zones.RegionZone
import rs.emulator.region.zones.RegionZoneEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class LeaveZoneEvent<A : IActor>(override val source: A, override val region: RegionZone) : RegionZoneEvent<A>