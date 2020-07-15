package rs.emulator.network.packet.decoder.impl

import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.AppletFocusEventMessage

/**
 *
 * @author Chk
 */
class AppletFocusEventDecoder : PacketDecoder<AppletFocusEventMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): AppletFocusEventMessage {

        return AppletFocusEventMessage()

    }

}