package rs.emulator.application

import com.google.common.util.concurrent.ServiceManager
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.definition.definition
import rs.emulator.cache.definition.entity.obj.ObjDefinition
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.Index
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.encryption.rsa.RSAService
import rs.emulator.fileserver.FileStoreService
import rs.emulator.network.pipeline.DefaultPipelineProvider
import rs.emulator.network.world.network.channel.pipeline.WorldPipelineProvider
import rs.emulator.network.world.service.WorldService
import java.nio.file.FileStore
import java.nio.file.Paths
import java.time.Duration

/**
 *
 * @author Chk
 */
class Test : KoinComponent
{

    private val fs: VirtualFileStore = get()

    private val world: WorldService = get()

    private val fileStoreService: FileStoreService = get()

    private val serviceManager: ServiceManager = get()

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

                single { DefinitionRepository() }

                single { DefaultPipelineProvider() }

                single { WorldService(WorldPipelineProvider()) }

                single { FileStoreService() }

                single { RSAService() }

                single { ServiceManager(listOf(
                    get<RSAService>(),
                    get<FileStoreService>(),
                    get<WorldService>()
                )) }

            }

            startKoin {

                modules(mod)

                val test = Test()

                test.serviceManager
                    .startAsync()
                    .awaitHealthy()

                System.gc()

            }

        }

    }

}