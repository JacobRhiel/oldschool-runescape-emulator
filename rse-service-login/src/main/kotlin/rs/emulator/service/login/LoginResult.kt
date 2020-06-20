package rs.emulator.service.login

import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
data class LoginResult(val status: LoginStatus) : NetworkMessage
{

    fun toByteBuf(channel: Channel): ByteBuf
    {

        var buffer: ByteBuf

        val allocator = channel.alloc()

        if(status != LoginStatus.ACCEPTED)
            buffer = allocator.buffer(1).writeByte(status.opcode)
        else
        {

            buffer = allocator.buffer(11)

            buffer.writeByte(status.opcode)

            buffer.writeByte(0)

            buffer.writeInt(0)

            buffer.writeByte(3)//rights

            buffer.writeBoolean(true)//members

            buffer.writeShort(1)//index

            buffer.writeBoolean(true)

        }

        return buffer

    }

}