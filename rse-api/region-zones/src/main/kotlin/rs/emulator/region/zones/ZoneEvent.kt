package rs.emulator.region.zones

import rs.emulator.entity.IEntity

/**
 *
 * @author javatar
 */

interface ZoneEvent<T : IEntity> {
    val entity: T
}