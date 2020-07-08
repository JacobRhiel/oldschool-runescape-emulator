package rs.emulator.application

import com.google.common.util.concurrent.ServiceManager
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import rs.emulator.Repository
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.definition.definition
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.encryption.rsa.RSAService
import rs.emulator.engine.service.CyclicEngineService
import rs.emulator.engine.service.schedule.CyclicDelaySchedule
import rs.emulator.world.task.UpdatePlayerSynchronizationTask
import rs.emulator.fileserver.FileStoreService
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.packet.repository.PacketService
import rs.emulator.network.pipeline.DefaultPipelineProvider
import rs.emulator.network.world.network.channel.pipeline.WorldPipelineProvider
import rs.emulator.network.world.service.WorldService
import rs.emulator.plugins.RSPluginManager
import rs.emulator.region.XteaKeyService
import rs.emulator.service.login.worker.LoginWorkerSchedule
import rs.emulator.service.login.worker.LoginWorkerService
import java.nio.file.Paths

/**
 *
 * @author Chk
 */
class Test : KoinComponent {

    private val serviceManager: ServiceManager = get()

    private val engine: CyclicEngineService = get()

    val packetRepository: PacketRepository = get()

    @ExperimentalStdlibApi
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val path = Paths.get("data/cache")

            val mod = module {

                single { DataFile(path.resolve("main_file_cache.dat2")) }

                single { ReferenceTable(path.resolve("main_file_cache.idx255")) }

                single { VirtualFileStore(path) }

                single { DefinitionRepository() }

                single { DefaultPipelineProvider() }

                single { WorldService(WorldPipelineProvider()) }

                single { FileStoreService() }

                single { XteaKeyService() }

                single { RSAService() }

                //single { JDBCPoolingService() }

                single { LoginWorkerSchedule() }

                single { LoginWorkerService() }

                single { CyclicDelaySchedule() }

                single { CyclicEngineService() }

                single { PacketService() }

                //todo move the parsing.
                single { PacketRepository() }

                single {
                    ServiceManager(
                        listOf(
                            // get<JDBCPoolingService>(),
                            get<RSAService>(),
                            get<FileStoreService>(),
                            get<XteaKeyService>(),
                            get<WorldService>(),
                            get<LoginWorkerService>(),
                            get<CyclicEngineService>(),
                            get<PacketService>()
                        )
                    )
                }

            }

            runBlocking {
                startKoin {

                    modules(mod)

                    val test = Test()
                    
                    RSPluginManager.apply {
                        Repository.definitionRepository = definition()
                        loadPlugins()
                        startPlugins()
                        println("Loaded ${plugins.size} plugins.")
                    }

                    test.engine.schedule(UpdatePlayerSynchronizationTask)

                    test.serviceManager
                        .startAsync()
                        .awaitHealthy()

                    System.gc()

                }
            }

        }

    }

}