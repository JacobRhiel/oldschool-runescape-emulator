package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.network.world.network.channel.message.WorldConnectionRequestMessage

/**
 *
 * @author Chk
 */
class WorldHeaderDecoder
    : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        if(buffer.readableBytes() < 3) return //todo throw error

        val length = buffer.readShort().toInt()

        if(buffer.readableBytes() < length) return //todo throw error

        val revision = buffer.readInt()

        buffer.readInt() //this is always 1

        val clientType = buffer.readByte().toInt()

        ctx.pipeline().remove(this)

        out.add(WorldConnectionRequestMessage(revision, clientType))

    }

}