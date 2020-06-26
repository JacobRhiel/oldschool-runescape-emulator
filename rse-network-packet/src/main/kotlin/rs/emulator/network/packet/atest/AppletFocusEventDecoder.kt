package rs.emulator.network.packet.atest

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder

/**
 *
 * @author Chk
 */
class AppletFocusEventDecoder : PacketDecoder<AppletFocusEventMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): AppletFocusEventMessage
    {

        return AppletFocusEventMessage()

    }

}