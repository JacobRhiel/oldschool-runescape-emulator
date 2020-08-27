package rs.emulator.network.packet.encoder.impl

import rs.emulator.buffer.manipulation.DataType
import rs.emulator.network.packet.GamePacketBuilder
import rs.emulator.network.packet.encoder.PacketEncoder
import rs.emulator.network.packet.message.outgoing.UpdateFriendListMessage

/**
 *
 * @author Chk
 */
class UpdateFriendListEncoder: PacketEncoder<UpdateFriendListMessage>()
{

    override fun encode(message: UpdateFriendListMessage, builder: GamePacketBuilder)
    {

        message.friends.forEach {

            val unknownBoolean = false //always false?

            builder.put(DataType.BYTE, if (unknownBoolean) 0 else 1)

            builder.putString(it.displayName())

            builder.putString(it.displayName()) //this is actually blank if no "display name" has been set and is pending final change.
            //otherwise it's actually the old display name, pending finalization.

            builder.put(DataType.SHORT, 1)//world id

            builder.put(DataType.BYTE, 0)//unknown mask

            builder.put(DataType.BYTE, 0)//unknown mask

            //if world id > 0 which means (if online)

            builder.putString("Old School 1")// 1 = world id

            builder.put(DataType.BYTE, 8)//unknown

            builder.put(DataType.INT, 0)//unknown

            builder.putString("")//blank string

        }

    }

}