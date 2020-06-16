package rs.emulator.network.service

import com.google.common.util.concurrent.AbstractIdleService
import io.netty.bootstrap.AbstractBootstrap
import io.netty.channel.*
import rs.emulator.network.channel.DefaultChannelInitializer
import rs.emulator.network.pipeline.PipelineProvider
import rs.emulator.utilities.logger.info

/**
 *
 * @author Chk
 */
abstract class AbstractNetworkService<T : AbstractBootstrap<*,*>, S : Channel>(
    childHandler: ChannelHandler,
    provider: PipelineProvider
)
    : AbstractIdleService()
{

    private val initializer = DefaultChannelInitializer<S>(childHandler, provider)

    private val bootstrap = this.configure(initializer)

    private lateinit var future: ChannelFuture

    abstract fun configure(initializer: ChannelInitializer<S>) : T

    abstract fun destroy()

    override fun startUp()
    {

        future = bootstrap.bind().sync()

        info("{} successfully bound to {}.")

        future.channel().closeFuture().sync()

    }

    override fun shutDown()
    {

        destroy()

        future.channel().closeFuture()

    }

}