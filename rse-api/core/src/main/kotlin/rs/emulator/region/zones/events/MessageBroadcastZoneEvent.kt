package rs.emulator.region.zones.events

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.region.zones.RegionZone
import rs.emulator.region.zones.RegionZoneEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class MessageBroadcastZoneEvent(val msg : String, override val source: IPlayer, override val region: RegionZone) :
    RegionZoneEvent<IPlayer>