package rs.emulator.network.packet.decoder.impl

import it.unimi.dsi.fastutil.objects.ObjectArrayList
import rs.emulator.buffer.manipulation.DataTransformation
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import rs.emulator.network.packet.message.incoming.KeyBoardEventMessage
import javax.swing.KeyStroke

/**
 *
 * @author Chk
 */
class KeyBoardEventDecoder : PacketDecoder<KeyBoardEventMessage>()
{

    override fun decode(opcode: Int, reader: GamePacketReader): KeyBoardEventMessage {

        val events = ObjectArrayList<KeyBoardEventMessage.KeyEvent>()

        while (reader.readableBytes > 0) {

            val key = reader.getSigned(DataType.BYTE, DataTransformation.ADD).toChar()

            val lastPress = reader.getUnsigned(DataType.TRI_BYTE)

            println("last press: $lastPress - key: ${KeyStroke.getKeyStroke(key)} - $key")

            events.add(KeyBoardEventMessage.KeyEvent(key, lastPress))

        }

        return KeyBoardEventMessage(events)

    }

}