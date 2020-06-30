package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.network.world.network.channel.message.WorldConnectionRequestMessage
import rs.emulator.network.world.network.channel.message.WorldHandshakeResponseMessage
import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol
import rs.emulator.utilities.logger.warn

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

        val worldRevision = 190 //todo: centralize

        if(revision != worldRevision)
        {

            warn("Client attempted to login to world with a different revision client. Revision $revision.")

            ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.REVISION_MISMATCH))

            ctx.channel().closeFuture()

            return

        }

        ctx.pipeline().remove(this)

    }

}