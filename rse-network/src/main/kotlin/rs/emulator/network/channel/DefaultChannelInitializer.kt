package rs.emulator.network.channel

import io.netty.channel.*
import io.netty.handler.timeout.IdleStateHandler
import io.netty.handler.traffic.ChannelTrafficShapingHandler
import io.netty.handler.traffic.GlobalTrafficShapingHandler
import rs.emulator.network.SESSION_KEY
import rs.emulator.network.pipeline.PipelineProvider
import rs.emulator.network.session.DefaultSession
import rs.emulator.network.session.NetworkSession
import java.util.concurrent.Executors

/**
 *
 * @author Chk
 */
class DefaultChannelInitializer<T : Channel>(
        private val childHandler: ChannelHandler,
        private val provider: PipelineProvider
)
    : ChannelInitializer<T>()
{

    private val globalTrafficHandler = GlobalTrafficShapingHandler(Executors.newSingleThreadScheduledExecutor(), 0, 0, 1000)

    override fun initChannel(channel: T)
    {

        val pipeline = channel.pipeline()

        //Set the default network session to handshake to handle channel local I/O operations.
        channel.attr(SESSION_KEY).set(DefaultSession())

        pipeline.addLast(GlobalTrafficShapingHandler::class.simpleName, globalTrafficHandler)

        pipeline.addLast(ChannelTrafficShapingHandler::class.simpleName, ChannelTrafficShapingHandler(0, 1024 * 5, 1000))

        pipeline.addLast(IdleStateHandler::class.simpleName, IdleStateHandler(30, 0, 0))

        //Unfortunately this is the neatest way as using *. results in the classes having #0 at the end of the name.
        provider.provide().forEach { provider -> pipeline.addLast(provider::class.simpleName, provider)  }

        pipeline.addLast(childHandler::class.simpleName, childHandler)

    }

}