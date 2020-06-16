package rs.emulator.network.world.network.channel.message

import io.netty.channel.ChannelHandlerContext
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.message.NetworkMessage
import rs.emulator.network.world.network.channel.protocol.WorldConnectionResponseProtocol
import rs.emulator.network.world.network.channel.session.WorldSession
import rs.emulator.utilities.logger.warn

/**
 *
 * @author Chk
 */
data class WorldConnectionRequestMessage(
    private val revision: Int,
    private val clientType: Int
) : NetworkMessage
{

    override fun handle(ctx: ChannelHandlerContext)
    {

        val session = ctx.channel().attr(SESSION_KEY).get()

        if(session !is WorldSession)
            throw Error("Not world session in: ${this::class.simpleName}.")

        val worldRevision = 189 //todo: centralize

        if(revision != worldRevision)
        {

            warn("Client attempted to login to world with a different revision client. Revision $revision.")

            ctx.writeAndFlush(WorldHandshakeResponseMessage(WorldConnectionResponseProtocol.REVISION_MISMATCH))

            ctx.channel().closeFuture()

            return

        }

    }

}