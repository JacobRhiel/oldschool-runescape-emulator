package rs.emulator.network.packet.atest

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class KeepAliveDecoder : PacketDecoder<KeepAliveMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): KeepAliveMessage
    {
        return KeepAliveMessage()
    }

}