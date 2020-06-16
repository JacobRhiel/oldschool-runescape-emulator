package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.buffer.BufferUtils.readString
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.world.network.channel.protocol.WorldConnectionRequestProtocol
import rs.emulator.network.world.network.channel.session.WorldSession

/**
 *
 * @author Chk
 */
class WorldAuthenticationDecoder(private val protocol: WorldConnectionRequestProtocol) : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is WorldSession)
            throw Error("Session is not WorldSession in WorldAuthenticationDecoder.")

        val secureBuffer = session.rsaBuffer

        if(protocol == WorldConnectionRequestProtocol.CONNECTION)
        {

            val authType = secureBuffer.readByte().toInt()

            if (authType == 1)
            {

                secureBuffer.readInt()

            }
            else if (authType == 0 || authType == 2)
            {
                secureBuffer.readUnsignedMedium()
                secureBuffer.skipBytes(Byte.SIZE_BYTES)
            }
            else
            {
                secureBuffer.readInt()
            }

            secureBuffer.skipBytes(Byte.SIZE_BYTES)

            val password = secureBuffer.readString()

            println("password: $password")

            //session.credentials.password = password

        }

        ctx.pipeline().remove(this)

    }

}