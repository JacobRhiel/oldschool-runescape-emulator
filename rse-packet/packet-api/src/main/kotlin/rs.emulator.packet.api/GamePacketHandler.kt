package rs.emulator.packet.api

import io.netty.channel.Channel
import rs.emulator.entity.actor.player.IPlayer

/**
 *
 * @author javatar
 */

interface GamePacketHandler {

    fun handle(player : IPlayer, message: IPacketMessage)

}