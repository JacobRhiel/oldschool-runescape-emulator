package rs.emulator.network.packet.atest

import it.unimi.dsi.fastutil.objects.ObjectArrayList
import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.actor.player.IPlayer
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketReader
import rs.emulator.network.packet.decoder.PacketDecoder
import javax.swing.KeyStroke

/**
 *
 * @author Chk
 */
class KeyBoardEventDecoder : PacketDecoder<KeyBoardEventMessage>()
{

    override fun decode(opcode: Int, player: Player, reader: GamePacketReader): KeyBoardEventMessage
    {

        val events = ObjectArrayList<KeyBoardEventMessage.KeyEvent>()

        println("test: " + reader.readableBytes)

        while(reader.readableBytes > 0)
        {

            val lastPress = reader.getUnsigned(DataType.TRI_BYTE)

            val key = reader.getUnsigned(DataType.BYTE).toChar()

            println("last press: $lastPress - key: ${KeyStroke.getKeyStroke(key)} - $key")

            events.add(KeyBoardEventMessage.KeyEvent(key, lastPress))

        }

        return KeyBoardEventMessage(events)

    }

}