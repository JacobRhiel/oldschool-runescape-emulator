package rs.emulator.fileserver.network.message

import io.netty.channel.ChannelHandlerContext
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.network.message.NetworkMessage

/**
 *
 * @author Chk
 */
data class JS5FileRequestMessage(
    val urgent: Boolean,
    val index: Int,
    val archive: Int
) : KoinComponent, NetworkMessage
{

    private val fileStore: VirtualFileStore = get()

    override fun handle(ctx: ChannelHandlerContext)
    {

        if (index == 255)
        {
            if (archive == 255)
            {

                val reader = fileStore.fetchIndexHeaderBuffer()

                println("this data is false: " + reader.toArray().toTypedArray().contentDeepToString())

                ctx.writeAndFlush(JS5FileResponseMessage(index, archive, reader))
            }
            else
                ctx.writeAndFlush(JS5FileResponseMessage(index, archive, fileStore.fetchIndexTableData(archive)))
        }
        else
            ctx.writeAndFlush(JS5FileResponseMessage(index, archive, fileStore.fetchArchiveCompressed(index, archive)))

    }

}