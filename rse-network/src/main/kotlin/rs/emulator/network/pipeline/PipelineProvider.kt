package rs.emulator.network.pipeline

import io.netty.channel.ChannelHandler
import it.unimi.dsi.fastutil.objects.ObjectArrayList

/**
 *
 * @author Chk
 */
interface PipelineProvider
{

    fun provide() : ObjectArrayList<ChannelHandler>

}