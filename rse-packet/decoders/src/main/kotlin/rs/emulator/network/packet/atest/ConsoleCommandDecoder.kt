package rs.emulator.network.packet.atest

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class ConsoleCommandDecoder : PacketDecoder<ConsoleCommandMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): ConsoleCommandMessage
    {

        println("viewport count: " + player.viewport.localPlayerCount)

        return ConsoleCommandMessage(reader.string)

    }

}