package rs.emulator.network.packet.atest

import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class ClientModDetectionDecoder : PacketDecoder<ClientModDetectionMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): ClientModDetectionMessage
    {

        return ClientModDetectionMessage()

    }

}