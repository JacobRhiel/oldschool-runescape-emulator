package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.WalkHereMessage
import rs.emulator.reactive.ReactiveZone
import rs.emulator.region.WorldCoordinate
import rs.emulator.region.zones.ZoneEvent
import rs.emulator.region.zones.events.EnterZoneEvent
import rs.emulator.region.zones.events.LeaveZoneEvent
import rs.emulator.world.World

/**
 *
 * @author Chk
 */
class WalkHereListener : GamePacketListener<WalkHereMessage>, KoinComponent {

    val world: World = get()

    override fun handle(channel: Channel, player: Player, message: WalkHereMessage) {

        println("attempting to walk: [miniMap = ${message.miniMap}, x: ${message.destX}, z: ${message.destZ}, teleport: ${message.teleportClick}.")

        if (message.teleportClick) {

            player.lastCoordinate.set(player.coordinate)

            player.coordinate.set(message.destX, message.destZ)

            player.pendingTeleport = player.coordinate

            player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)

        } else {

            player.world.mapGrid.fetchRegion(player.coordinate.x, player.coordinate.z)

            val path = player.find(WorldCoordinate(message.destX, message.destZ, player.coordinate.plane))

            println("Path dest $path")

        }
        val region = world.mapGrid.fetchRegion(player.coordinate.toRegion().regionId)

        region.zones.forEach {
            handleZone(it.reactiveZone, player)
        }

        /*fireZoneEvents(
            region.zones.flatMap { it.reactiveZone }.toList(),
            player
        )*/

    }

    private fun fireZoneEvents(zone: List<ReactiveZone<ZoneEvent<*>>>, player: Player) {
        zone.forEach {
            fireZoneEvents(it.children, player)
            handleZone(it, player)
        }
    }

    private fun handleZone(
        zone: ReactiveZone<ZoneEvent<*>>,
        player: Player
    ) {
        if (zone.isWithin(player.lastCoordinate.x, player.lastCoordinate.z) && !zone.isWithin(
                player.coordinate.x,
                player.coordinate.z
            )
        ) {
            zone.onNext(LeaveZoneEvent(player))
        } else if (zone.isWithin(player.coordinate.x, player.coordinate.z) && !zone.isWithin(
                player.lastCoordinate.x,
                player.lastCoordinate.z
            )
        ) {
            zone.onNext(EnterZoneEvent(player))
        }
    }


}