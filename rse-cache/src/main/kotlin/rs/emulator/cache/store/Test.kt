package rs.emulator.cache.store

import org.koin.core.*
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.reference.ReferenceTable
import java.io.File
import java.nio.file.Paths
import kotlin.collections.get

/**
 *
 * @author Chk
 */
class Test : KoinComponent
{

    private val fs: VirtualFileStore = get()

    companion object
    {

        @JvmStatic
        fun main(args: Array<String>)
        {

            val path = Paths.get("./cache/")

            val mod = module {

                single { DataFile(path.resolve("main_file_cache.dat2"))  }

                single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

                single { VirtualFileStore(path) }

            }

            startKoin {

                modules(mod)

                val test = Test()

                val idx = test.fs.fetchIndex(2)

                //idx.table.load(2, idx.decompressedBuffer)

                val archive = idx.fetchArchive(2)

                archive.fetchEntry(1)

            }

        }

    }

}