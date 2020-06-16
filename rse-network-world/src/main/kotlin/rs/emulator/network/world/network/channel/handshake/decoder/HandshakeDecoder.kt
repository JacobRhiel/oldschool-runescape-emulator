package rs.emulator.network.world.network.channel.handshake.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import rs.emulator.network.world.network.channel.handshake.HandshakeRequestProtocol
import rs.emulator.network.world.network.channel.handshake.HandshakeResponseProtocol
import rs.emulator.network.world.network.channel.handshake.message.HandshakeResponseMessage
import rs.emulator.utilities.logger.debug
import rs.emulator.utilities.logger.error
import rs.emulator.utilities.logger.logger

/**
 *
 * @author Chk
 */
class HandshakeDecoder
    : ByteToMessageDecoder()
{

    override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>)
    {

        if(!buffer.isReadable) return

        val opcode = buffer.readByte().toInt()

        val protocol = HandshakeRequestProtocol.fetchProtocol(opcode)

        var response = HandshakeResponseProtocol.ALLOW_HANDSHAKE

        if(protocol == null)
        {
            error("No HandshakeProtocol exists for opcode: {}.", opcode)
            response = HandshakeResponseProtocol.REJECT_HANDSHAKE
            return
        }

        debug("Handling decoder for protocol: {}.", protocol)

        ctx.pipeline().remove(this)

        out.add(HandshakeResponseMessage(protocol, response))

    }

}