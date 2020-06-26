package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class ClientModDetectionDecoder : PacketDecoder<ClientModDetectionMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ClientModDetectionMessage
    {

        return ClientModDetectionMessage()

    }

}