package rs.emulator.network.pipeline

import io.netty.channel.ChannelHandler
import it.unimi.dsi.fastutil.objects.ObjectArrayList

/**
 *
 * @author Chk
 */
class DefaultPipelineProvider : PipelineProvider
{

    override fun provide(): ObjectArrayList<ChannelHandler>
    {
        return ObjectArrayList()
    }

}