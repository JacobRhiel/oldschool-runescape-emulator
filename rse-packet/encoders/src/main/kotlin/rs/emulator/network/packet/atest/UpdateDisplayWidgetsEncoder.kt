package rs.emulator.network.packet.atest

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.entity.player.Player
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder

/**
 *
 * @author Chk
 */
class UpdateDisplayWidgetsEncoder : PacketEncoder<UpdateDisplayWidgetsMessage>()
{

    override fun encode(message: UpdateDisplayWidgetsMessage, player: Player, builder: GamePacketBuilder)
    {

        builder.put(DataType.SHORT, 165) //root id

        builder.put(DataType.SHORT, 21) //root children count

        sendInterface(builder, 10813451, 320, 1)
        sendInterface(builder, 10813462, 216, 1)
        sendInterface(builder, 10813466, 160, 1)
        sendInterface(builder, 10813450, 593, 1)
        sendInterface(builder, 10813441, 162, 1)
        sendInterface(builder, 10813446, 122, 1)
        sendInterface(builder, 10813469, 378, 0)
        sendInterface(builder, 10813456, 218, 1)
        sendInterface(builder, 10813442, 651, 1)
        sendInterface(builder, 10813465, 163, 1)
        sendInterface(builder, 10813454, 387, 1)
        sendInterface(builder, 41222177, 76, 1)
        sendInterface(builder, 10813452, 629, 1)
        sendInterface(builder, 10813453, 149, 1)
        sendInterface(builder, 10813455, 541, 1)
        sendInterface(builder, 10813459, 429, 1)
        sendInterface(builder, 10813458, 109, 1)
        sendInterface(builder, 10813461, 261, 1)
        sendInterface(builder, 10813460, 182, 1)
        sendInterface(builder, 10813463, 239, 1)
        sendInterface(builder, 10813457, 7, 1)

        sendAccessMask(builder, 4980762, 0, 20, 2)
        sendAccessMask(builder, 14155777, 0, 48, 2)
        sendAccessMask(builder, 14287033, 0, 4, 2)
        sendAccessMask(builder, 15663107, 0, 640, 6)
        sendAccessMask(builder, 17105004, 1, 4, 2)
        sendAccessMask(builder, 17105005, 1, 4, 2)

    }

    private fun sendInterface(builder: GamePacketBuilder, hash: Int, id: Int, toggle: Int)
    {

        builder.put(DataType.INT, hash)
        builder.put(DataType.SHORT, id)
        builder.put(DataType.BYTE, toggle)

    }

    private fun sendAccessMask(builder: GamePacketBuilder, hash: Int, minSlot: Int, maxSlot: Int, clickMaskType: Int)
    {

        builder.put(DataType.INT, hash)
        builder.put(DataType.SHORT, minSlot)
        builder.put(DataType.SHORT, maxSlot)
        builder.put(DataType.INT, clickMaskType)

    }

}