package rs.emulator.fileserver.network.channel.pipeline

import io.netty.channel.ChannelHandler
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import rs.emulator.fileserver.network.channel.decoder.JS5HandshakeDecoder
import rs.emulator.fileserver.network.channel.encoder.*
import rs.emulator.network.pipeline.PipelineProvider

/**
 *
 * @author Chk
 */
class JS5PipelineProvider
    : PipelineProvider
{

    override fun provide(): ObjectArrayList<ChannelHandler> = ObjectArrayList.wrap(
        arrayOf(
            JS5HandshakeResponseEncoder(),
            JS5HandshakeDecoder()
        )
    )

}