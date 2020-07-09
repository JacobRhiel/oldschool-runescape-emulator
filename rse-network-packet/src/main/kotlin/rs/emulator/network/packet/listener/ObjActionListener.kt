package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.atest.ObjActionMessage

/**
 *
 * @author Chk
 */
class ObjActionListener : GamePacketListener<ObjActionMessage>
{
    override fun handle(channel: Channel, player: Player, message: ObjActionMessage)
    {

        println("[ObjAction] - ${message.item}, ${message.option}, ${message.componentHash}")

    }
}