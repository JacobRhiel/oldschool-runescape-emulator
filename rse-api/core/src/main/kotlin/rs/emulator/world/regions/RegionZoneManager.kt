package rs.emulator.world.regions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import rs.emulator.entity.actor.IActor
import rs.emulator.reactive.launch
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.zones.*
import rs.emulator.regions.zones.*
import rs.emulator.region.zones.bounds.RectangularArea
import rs.emulator.region.zones.events.EnterZoneEvent
import rs.emulator.region.zones.events.LeaveZoneEvent
import rs.emulator.region.zones.events.ZoneRestrictionEvent

/**
 *
 * @author javatar
 */

@ExperimentalCoroutinesApi
object RegionZoneManager {

    private val regionZones: MutableMap<String, RegionZone> = mutableMapOf()

    init {
        WorldCoordinate(x = 3222, z = 3218)
        registerRegionZone(
            RegionZone(
                "test",
                RectangularArea.create(3222, 3218, 10, 10)
            )
        ).consumeEachEvent<ZoneRestrictionEvent> {
            it.restricted = true
        /*}.consumeEachEvent<PlayerEnterZoneEvent> {
            it.source.affectHandler.addIntervalAffect(
                name = "msg",
                period = 5000,
                affect = MessageAffect("Hello World", it.source)
            )*/
        }.consumeEachEvent<PlayerLeaveZoneEvent> {
            it.source.affectHandler.cancelAffectByName("msg")
        }.launch()

        assert(regionZones.containsKey("test"))
    }

    fun registerRegionZone(zone: RegionZone): Flow<RegionZoneEvent<*>> {
        regionZones.putIfAbsent(zone.zoneName, zone)
        return zone.events.openSubscription().consumeAsFlow()
    }

    fun getZone(zoneName: String): RegionZone {
        return regionZones.getOrDefault(zoneName, RegionZone.WORLD)
    }

    fun updateActorZone(actor: IActor) {
        val prevZone = actor.currentZone.value
        if (prevZone === RegionZone.WORLD) {
            val currentZone =
                regionZones.values.firstOrNull { it.bounds.intersects(actor.coordinate) } ?: RegionZone.WORLD
            if (currentZone != RegionZone.WORLD) {
                actor.currentZone.value = currentZone
                actor.currentZone.sendEvent(EnterZoneEvent(actor, currentZone))
            }
        } else if (!prevZone.bounds.intersects(actor.coordinate)) {
            val currentZone =
                regionZones.values.firstOrNull { it.bounds.intersects(actor.coordinate) } ?: RegionZone.WORLD
            prevZone.sendEvent(LeaveZoneEvent(actor, prevZone))
            if (currentZone !== RegionZone.WORLD) {
                currentZone.sendEvent(EnterZoneEvent(actor, currentZone))
            }
            actor.currentZone.value = currentZone
        }
    }

}