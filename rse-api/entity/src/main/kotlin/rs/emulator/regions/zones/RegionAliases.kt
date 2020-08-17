package rs.emulator.regions.zones

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.regions.zones.events.EnterZoneEvent
import rs.emulator.regions.zones.events.LeaveZoneEvent

/**
 *
 * @author javatar
 */

@OptIn(ExperimentalCoroutinesApi::class)
typealias PlayerEnterZoneEvent = EnterZoneEvent<IPlayer>
@OptIn(ExperimentalCoroutinesApi::class)
typealias PlayerLeaveZoneEvent = LeaveZoneEvent<IPlayer>
@OptIn(ExperimentalCoroutinesApi::class)
typealias NpcEnterZoneEvent = EnterZoneEvent<INpc>
@OptIn(ExperimentalCoroutinesApi::class)
typealias NpcLeaveZoneEvent = LeaveZoneEvent<INpc>
