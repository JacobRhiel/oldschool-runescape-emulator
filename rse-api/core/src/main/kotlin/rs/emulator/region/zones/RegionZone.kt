package rs.emulator.region.zones

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import rs.emulator.entity.IEntity
import rs.emulator.entity.actor.npc.INpc
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.region.zones.bounds.RectangularArea
import rs.emulator.region.zones.events.EnterZoneEvent
import rs.emulator.region.zones.events.LeaveZoneEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
data class RegionZone(
    val zoneName: String,
    val bounds: ZoneBounds,
    val players: MutableList<IPlayer> = mutableListOf(),
    val npcs: MutableList<INpc> = mutableListOf(),
    val events: BroadcastChannel<RegionZoneEvent<*>> = ConflatedBroadcastChannel()
) {

    fun <E : IEntity> sendEvent(event: RegionZoneEvent<E>) {
        if (event is EnterZoneEvent && event.source is IPlayer) {
            players.add(event.source as IPlayer)
        } else if (event is EnterZoneEvent && event.source is INpc) {
            npcs.add(event.source as INpc)
        } else if (event is LeaveZoneEvent && event.source is IPlayer) {
            players.remove(event.source as IPlayer)
        } else if (event is LeaveZoneEvent && event.source is INpc) {
            npcs.remove(event.source as INpc)
        }
        events.sendBlocking(event)
    }

    companion object {

        val WORLD = RegionZone("world", RectangularArea(0, 0, 0, 0, 0))

    }
}