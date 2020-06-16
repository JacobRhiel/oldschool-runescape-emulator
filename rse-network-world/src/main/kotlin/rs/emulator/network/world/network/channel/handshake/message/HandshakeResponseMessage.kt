package rs.emulator.network.world.network.channel.handshake.message

import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.world.network.channel.handshake.HandshakeRequestProtocol
import rs.emulator.network.world.network.channel.handshake.HandshakeResponseProtocol
import rs.emulator.utilities.logger.warn

/**
 *
 * @author Chk
 */
data class HandshakeResponseMessage(val request: HandshakeRequestProtocol,
                                    val response: HandshakeResponseProtocol
)
    : NetworkMessage
{

    override fun handle(ctx: ChannelHandlerContext)
    {

        if(response == HandshakeResponseProtocol.REJECT_HANDSHAKE)
        {
            warn("Handshake rejected for address {}.")
            ctx.channel().closeFuture()
            return
        }
        else ctx.writeAndFlush(RequestHandshakePipelineMessage(request))

    }

}