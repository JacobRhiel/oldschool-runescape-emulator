package rs.emulator.fileserver.network.channel.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.fileserver.network.session.JS5Session
import rs.emulator.network.SESSION_KEY

/**
 *
 * @author Chk
 */
class JS5XOREncoder
    : MessageToByteEncoder<ByteBuf>()
{

    override fun encode(ctx: ChannelHandlerContext, read: ByteBuf, out: ByteBuf)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is JS5Session)
            error("JS5Session request on non JS5 session.")
        else
        {

            val key = session.xorKey

            val data = ByteArray(read.readableBytes())

            read.readBytes(data)

            for (index in data.indices)
                data[index] = (data[index].toInt() xor key).toByte()

            out.writeBytes(data)

        }
    }

}