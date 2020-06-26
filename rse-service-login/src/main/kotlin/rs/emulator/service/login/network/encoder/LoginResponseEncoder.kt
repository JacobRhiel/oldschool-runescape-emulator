package rs.emulator.service.login.network.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.packet.decoder.GamePacketDecoder
import rs.emulator.network.packet.encoder.GamePacketEncoder
import rs.emulator.network.packet.encoder.GamePacketMessageEncoder
import rs.emulator.network.packet.session.PacketSession
import rs.emulator.service.login.LoginStatus
import rs.emulator.service.login.network.message.LoginResponseMessage

/**
 *
 * @author Chk
 */
class LoginResponseEncoder : MessageToByteEncoder<LoginResponseMessage>()
{

    override fun encode(ctx: ChannelHandlerContext, msg: LoginResponseMessage, out: ByteBuf)
    {

        val response = msg.response

        val buffer = response.toByteBuf(ctx.channel())

        if(response.status == LoginStatus.ACCEPTED)
        {

            out.writeBytes(buffer)

            println("login response time.")

        }
        else
            ctx.channel().writeAndFlush(buffer)

        ctx.pipeline().remove(this)

    }

}