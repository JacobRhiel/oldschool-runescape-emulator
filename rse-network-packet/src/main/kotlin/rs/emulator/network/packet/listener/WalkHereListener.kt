package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.WalkHereMessage
import rs.emulator.region.WorldCoordinate
import rs.emulator.world.World
import rs.emulator.world.regions.RegionZoneManager

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
class WalkHereListener : GamePacketListener<WalkHereMessage>, KoinComponent {

    val world: World = get()

    override fun handle(
        player: Player,
        message: WalkHereMessage
    ) {

        //println("attempting to walk: [miniMap = ${message.miniMap}, x: ${message.destX}, z: ${message.destZ}, teleport: ${message.teleportClick}.")

        if (message.teleportClick) {

            player.lastCoordinate.set(player.coordinate)

            player.coordinate.set(message.destX, message.destZ)

            player.pendingTeleport = player.coordinate

            player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)

        } else {

            player.world.mapGrid.fetchRegion(player.coordinate.x, player.coordinate.y)

            val path = player.find(WorldCoordinate(message.destX, message.destZ, player.coordinate.plane))

            player.targetCoordinate = WorldCoordinate(message.destX, message.destZ, player.coordinate.plane)

            //println("Path dest $path")
        }

        RegionZoneManager.updateActorZone(player)

    }


}