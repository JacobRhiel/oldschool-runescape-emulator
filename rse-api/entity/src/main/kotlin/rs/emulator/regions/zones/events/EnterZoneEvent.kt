package rs.emulator.regions.zones.events

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.IActor
import rs.emulator.regions.zones.RegionZone
import rs.emulator.regions.zones.RegionZoneEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class EnterZoneEvent<A : IActor>(override val source: A, override val region: RegionZone) : RegionZoneEvent<A>