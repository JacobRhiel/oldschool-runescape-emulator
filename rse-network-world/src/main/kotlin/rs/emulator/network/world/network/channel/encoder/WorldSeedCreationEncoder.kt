package rs.emulator.network.world.network.channel.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.world.network.channel.message.WorldSeedCreationMessage
import rs.emulator.network.world.network.channel.session.WorldSession

/**
 *
 * @author Chk
 */
class WorldSeedCreationEncoder
    : MessageToByteEncoder<WorldSeedCreationMessage>()
{

    override fun encode(ctx: ChannelHandlerContext, msg: WorldSeedCreationMessage, buffer: ByteBuf)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is WorldSession)
            throw Error("Not world session.")

        //header empty byte
        buffer.writeByte(0)

        buffer.writeLong(session.fetchSeed())

        ctx.pipeline().remove(this)

    }

}