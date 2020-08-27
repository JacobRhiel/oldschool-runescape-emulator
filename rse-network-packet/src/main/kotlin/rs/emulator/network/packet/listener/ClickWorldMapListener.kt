package rs.emulator.network.packet.listener

import kotlinx.coroutines.ExperimentalCoroutinesApi
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.incoming.ClickWorldMapMessage
import rs.emulator.region.coordinate.Coordinate
import rs.emulator.region.set

/**
 *
 * @author Chk
 */
@ExperimentalCoroutinesApi
class ClickWorldMapListener : GamePacketListener<ClickWorldMapMessage>
{

    override fun handle(player: Player, message: ClickWorldMapMessage)
    {

        player.lastCoordinate.set(player.coordinate)

        player.coordinateState.set(Coordinate.from30BitHash(message.coordinateHash))

        player.pendingTeleport = player.coordinate

        player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)

    }

}