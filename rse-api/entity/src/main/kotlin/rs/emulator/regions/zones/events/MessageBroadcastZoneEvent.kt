package rs.emulator.regions.zones.events

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.regions.zones.RegionZone
import rs.emulator.regions.zones.RegionZoneEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class MessageBroadcastZoneEvent(val msg : String, override val source: IPlayer, override val region: RegionZone) : RegionZoneEvent<IPlayer>