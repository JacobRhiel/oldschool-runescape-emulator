package rs.emulator.network.world.network.channel.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.world.network.channel.message.WorldHandshakeResponseMessage
import rs.emulator.network.world.network.channel.protocol.WorldConnectionRequestProtocol
import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol
import rs.emulator.utilities.logger.error

/**
 *
 * @author Chk
 */
class WorldHandshakeDecoder
    : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        if(!buffer.isReadable) return //todo error message

        val opcode = buffer.readByte().toInt()

        val protocol = WorldConnectionRequestProtocol.fetchProtocol(opcode)

        if(protocol == null)
        {

            error("Non-existent protocol for opcode: {}.", opcode)

            ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.BAD_SESSION_ID))

            return

        }

        ctx.pipeline().addBefore(DefaultChannelHandler::class.simpleName, WorldHeaderDecoder::class.simpleName, WorldHeaderDecoder())

        ctx.pipeline().addAfter(WorldHeaderDecoder::class.simpleName, WorldRSAEncryptionDecoder::class.simpleName, WorldRSAEncryptionDecoder(protocol))

        ctx.pipeline().addAfter(WorldRSAEncryptionDecoder::class.simpleName, WorldAuthenticationDecoder::class.simpleName, WorldAuthenticationDecoder(protocol))

        ctx.pipeline().addAfter(WorldAuthenticationDecoder::class.simpleName, WorldIsaacEncryptionDecoder::class.simpleName, WorldIsaacEncryptionDecoder())

/*

        ctx.pipeline().addAfter(WorldRSAEncryptionDecoder::class.simpleName, WorldAuthenticationDecoder::class.simpleName, WorldAuthenticationDecoder(protocol))

        ctx.pipeline().addAfter(WorldAuthenticationDecoder::class.simpleName, WorldIsaacEncryptionDecoder::class.simpleName, WorldIsaacEncryptionDecoder())

*/

        ctx.pipeline().remove(this)

    }

}