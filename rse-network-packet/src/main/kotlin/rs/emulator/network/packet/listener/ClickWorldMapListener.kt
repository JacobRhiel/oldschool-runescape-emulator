package rs.emulator.network.packet.listener

import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.ClickWorldMapMessage
import rs.emulator.region.coordinate.Coordinate

/**
 *
 * @author Chk
 */
class ClickWorldMapListener : GamePacketListener<ClickWorldMapMessage>
{

    override fun handle(player: Player, message: ClickWorldMapMessage)
    {

        player.lastCoordinate.set(player.coordinate)

        val coordinate = Coordinate.from30BitHash(message.coordinateHash)

        player.coordinate.set(coordinate.x, coordinate.y)

        player.pendingTeleport = player.coordinate

        player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)

    }

}