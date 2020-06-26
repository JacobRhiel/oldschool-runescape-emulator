package rs.emulator.network.world.network.channel.handshake.encoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import rs.emulator.fileserver.network.channel.decoder.JS5HandshakeDecoder
import rs.emulator.fileserver.network.channel.encoder.JS5HandshakeResponseEncoder
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.world.network.channel.handshake.HandshakeRequestProtocol
import rs.emulator.network.world.network.channel.handshake.HandshakeRequestProtocol.*
import rs.emulator.network.world.network.channel.handshake.message.RequestHandshakePipelineMessage
import rs.emulator.network.world.network.channel.message.WorldSeedCreationMessage
import rs.emulator.network.world.network.channel.session.WorldSession

/**
 *
 * @author Chk
 */
class HandshakeEncoder: MessageToByteEncoder<RequestHandshakePipelineMessage>()
{

    override fun encode(ctx: ChannelHandlerContext, msg: RequestHandshakePipelineMessage, buffer: ByteBuf)
    {

        println("request: " + msg.request)

        when(msg.request)
        {

            WORLD_SERVER_CONNECTION ->
            {

                //Remove all JS5 related handlers since they are not currently required.
                ctx.pipeline().forEach {

                    if(it.key.contains("js5", true))
                        ctx.pipeline().remove(it.value)

                }

                ctx.channel().attr(SESSION_KEY).set(WorldSession())

                ctx.channel().writeAndFlush(WorldSeedCreationMessage())


            }

            FILE_SERVER_CONNECTION ->
            {

                //Remove all world related handlers since they are not currently required.
                ctx.pipeline().forEach {

                    if(it.key.contains("world", true))
                        ctx.pipeline().remove(it.value)

                }

               // worldProvider.handlers().forEach { ctx.pipeline().remove(it::class.simpleName) }

            }

        }

        ctx.pipeline().remove(this)

    }

}