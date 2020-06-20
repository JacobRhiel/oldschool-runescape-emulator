@file:Suppress("UnstableApiUsage")

package rs.emulator.fileserver

import com.google.common.util.concurrent.AbstractIdleService
import org.koin.core.KoinComponent
import org.koin.core.get
import rs.emulator.cache.store.VirtualFileStore

/**
 *
 * @author Chk
 */
class FileStoreService
    : KoinComponent, AbstractIdleService()
{

    private val fileStore: VirtualFileStore = get()

    override fun startUp()
    {

        fileStore.preload()

    }

    override fun shutDown()
    {

    }

}