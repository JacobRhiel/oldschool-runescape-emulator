package rs.emulator.network.packet.listener

import io.netty.channel.Channel
import rs.emulator.network.packet.message.GamePacketMessage

/**
 *
 * @author Chk
 */
interface GamePacketListener<T : GamePacketMessage>
{

    fun handle(channel: Channel, message: T)

}