package rs.emulator.fileserver.network.channel.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.fileserver.network.message.JS5FileResponseMessage

/**
 *
 * @author Chk
 */
class JS5FileEncoder
    : MessageToByteEncoder<JS5FileResponseMessage>()
{

    override fun encode(ctx: ChannelHandlerContext, msg: JS5FileResponseMessage, out: ByteBuf)
    {

        out.writeByte(msg.index)
        out.writeShort(msg.archive)

        //println(msg.buffer.toArray().toTypedArray().contentDeepToString())

        msg.buffer.toArray().forEach { data ->
                if (out.writerIndex() % 512 == 0)
                    out.writeByte(-1)
                out.writeByte(data.toInt())
            }

    }

}