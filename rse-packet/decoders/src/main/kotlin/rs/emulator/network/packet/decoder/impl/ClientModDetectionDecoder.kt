package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.ClientModDetectionMessage

/**
 *
 * @author Chk
 */
class ClientModDetectionDecoder : PacketDecoder<ClientModDetectionMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): ClientModDetectionMessage {

        return ClientModDetectionMessage()

    }

}