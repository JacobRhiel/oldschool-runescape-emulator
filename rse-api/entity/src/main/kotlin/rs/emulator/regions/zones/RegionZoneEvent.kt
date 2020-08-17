package rs.emulator.regions.zones

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.IEntity

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
interface RegionZoneEvent<S : IEntity> {
    val source: S
    val region: RegionZone
}