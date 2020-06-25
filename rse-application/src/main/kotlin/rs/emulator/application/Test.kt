package rs.emulator.application

import com.google.common.util.concurrent.ServiceManager
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import rs.emulator.Repository
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.encryption.rsa.RSAService
import rs.emulator.fileserver.FileStoreService
import rs.emulator.network.pipeline.DefaultPipelineProvider
import rs.emulator.network.world.network.channel.pipeline.WorldPipelineProvider
import rs.emulator.network.world.service.WorldService
import rs.emulator.service.login.worker.LoginWorkerSchedule
import rs.emulator.service.login.worker.LoginWorkerService
import java.nio.file.Paths

/**
 *
 * @author Chk
 */
class Test : KoinComponent
{

    private val fs: VirtualFileStore = get()

    private val world: WorldService = get()

    private val fileStoreService: FileStoreService = get()

    private val databaseService: JDBCPoolingService = get()

    private val serviceManager: ServiceManager = get()

    @ExperimentalStdlibApi
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

                single { DefinitionRepository().apply { Repository.repository = this } }

                single { DefaultPipelineProvider() }

                single { WorldService(WorldPipelineProvider()) }

                single { FileStoreService() }

                single { RSAService() }

                single { JDBCPoolingService() }

                single { LoginWorkerSchedule() }

                single { LoginWorkerService() }

                single { ServiceManager(listOf(
                    get<JDBCPoolingService>(),
                    get<RSAService>(),
                    get<FileStoreService>(),
                    get<WorldService>(),
                    get<LoginWorkerService>()
                )) }

            }

            startKoin {

                modules(mod)

                val test = Test()

                test.serviceManager
                    .startAsync()
                    .awaitHealthy()

                System.gc()

                //val world = WorldBuilder().setActivity(WorldActivity.NONE).setMembers(true).setOrigin(WorldOrigin.UNITED_STATES).build()

                //world.save(world)

            }

        }

    }

}