package rs.emulator.region.zones.events

import rs.emulator.entity.actor.IActor
import rs.emulator.region.zones.ZoneEvent

/**
 *
 * @author javatar
 */

data class EnterZoneEvent(override val entity: IActor) :
    ZoneEvent<IActor>