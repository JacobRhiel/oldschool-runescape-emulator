package rs.emulator.network.world.network.channel.pipeline

import io.netty.channel.ChannelHandler
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.fileserver.network.channel.decoder.JS5HandshakeDecoder
import rs.emulator.fileserver.network.channel.encoder.JS5HandshakeResponseEncoder
import rs.emulator.network.pipeline.DefaultPipelineProvider
import rs.emulator.network.pipeline.PipelineProvider
import rs.emulator.network.world.network.channel.decoder.WorldHandshakeDecoder
import rs.emulator.network.world.network.channel.encoder.WorldSeedCreationEncoder
import rs.emulator.network.world.network.channel.handshake.decoder.HandshakeDecoder
import rs.emulator.network.world.network.channel.handshake.encoder.HandshakeEncoder

/**
 *
 * @author Chk
 */
class WorldPipelineProvider : KoinComponent, PipelineProvider
{

    private val defaultProvider: DefaultPipelineProvider = get()

    override fun provide(): ObjectArrayList<ChannelHandler>
    {

        val defaults = defaultProvider.provide()

        defaults.add(HandshakeEncoder())

        defaults.add(HandshakeDecoder())

        defaults.add(JS5HandshakeResponseEncoder())

        defaults.add(JS5HandshakeDecoder())

        defaults.add(WorldSeedCreationEncoder())

        defaults.add(WorldHandshakeDecoder())

        return defaults

    }

}