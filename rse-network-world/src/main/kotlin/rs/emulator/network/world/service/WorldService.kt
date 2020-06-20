package rs.emulator.network.world.service

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.fileserver.FileStoreService
import rs.emulator.network.channel.DefaultChannelHandler
import rs.emulator.network.pipeline.PipelineProvider
import rs.emulator.network.service.AbstractNetworkService

/**
 *
 * @author Chk
 */
class WorldService(handshakeProvider: PipelineProvider) :
    AbstractNetworkService<ServerBootstrap, NioServerSocketChannel>(
        childHandler = DefaultChannelHandler(),
        provider = handshakeProvider
    ), KoinComponent
{

    private lateinit var acceptGroup: NioEventLoopGroup

    private lateinit var ioGroup: NioEventLoopGroup

    private val fileStoreService: FileStoreService = get()

    override fun startUp()
    {

        fileStoreService.awaitRunning()

        super.startUp()

    }

    override fun configure(initializer: ChannelInitializer<NioServerSocketChannel>): ServerBootstrap
    {

        val bootstrap = ServerBootstrap()

        acceptGroup = NioEventLoopGroup(2)

        ioGroup = NioEventLoopGroup(1)

        bootstrap
                .group(acceptGroup, ioGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(initializer)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .localAddress(43594)

        return bootstrap

    }

    override fun destroy()
    {

        acceptGroup.shutdownGracefully()

        ioGroup.shutdownGracefully()

    }

}