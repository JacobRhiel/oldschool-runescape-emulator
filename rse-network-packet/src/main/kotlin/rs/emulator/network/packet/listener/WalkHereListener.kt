package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.entity.player.update.flag.PlayerUpdateFlag
import rs.emulator.network.packet.message.WalkHereMessage

/**
 *
 * @author Chk
 */
class WalkHereListener : GamePacketListener<WalkHereMessage>
{

    override fun handle(channel: Channel, player: Player, message: WalkHereMessage)
    {

        println("attempting to walk: [miniMap = ${message.miniMap}, x: ${message.destX}, z: ${message.destZ}, teleport: ${message.teleportClick}.")

        if(message.teleportClick)
        {

            player.lastCoordinate.set(player.coordinate)

            player.coordinate.set(message.destX, message.destZ)

            player.syncInfo.addMaskFlag(PlayerUpdateFlag.MOVEMENT)

        }

    }

}