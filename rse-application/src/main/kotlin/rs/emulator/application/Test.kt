package rs.emulator.application

import com.google.common.util.concurrent.ServiceManager
import com.google.gson.GsonBuilder
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.dsl.module
import rs.dusk.engine.model.world.map.collision.Collisions
import rs.dusk.engine.path.PathFinder
import rs.dusk.engine.path.find.AxisAlignment
import rs.dusk.engine.path.find.BreadthFirstSearch
import rs.dusk.engine.path.find.DirectSearch
import rs.emulator.Repository
import rs.emulator.cache.definition.DefinitionRepository
import rs.emulator.cache.definition.definition
import rs.emulator.cache.store.VirtualFileStore
import rs.emulator.cache.store.data.DataFile
import rs.emulator.cache.store.index.IndexConfig
import rs.emulator.cache.store.reference.ReferenceTable
import rs.emulator.database.service.JDBCPoolingService
import rs.emulator.encryption.huffman.HuffmanCodec
import rs.emulator.encryption.rsa.RSAService
import rs.emulator.encryption.xtea.XteaKeyService
import rs.emulator.engine.service.CyclicEngineService
import rs.emulator.engine.service.schedule.CyclicDelaySchedule
import rs.emulator.entity.material.items.Item
import rs.emulator.fileserver.FileStoreService
import rs.emulator.gson.ItemAdapter
import rs.emulator.network.packet.PacketService
import rs.emulator.network.packet.repository.PacketRepository
import rs.emulator.network.pipeline.DefaultPipelineProvider
import rs.emulator.network.world.network.channel.pipeline.WorldPipelineProvider
import rs.emulator.network.world.service.WorldService
import rs.emulator.plugins.RSPluginManager
import rs.emulator.service.login.worker.LoginWorkerSchedule
import rs.emulator.service.login.worker.LoginWorkerService
import rs.emulator.utilities.contexts.scopes.ActorScope
import rs.emulator.world.World
import rs.emulator.world.WorldAccess
import rs.emulator.world.WorldActivity
import rs.emulator.world.WorldOrigin
import rs.emulator.world.repository.task.PlayerWalkingTask
import rs.emulator.world.repository.task.PreUpdatePlayerSynchronizationTask
import rs.emulator.world.repository.task.UpdateNpcSynchronizationTask
import rs.emulator.world.repository.task.UpdatePlayerSynchronizationEvent
import java.nio.file.Paths

/**
 *
 * @author Chk
 */
class Test : KoinComponent {

    private val serviceManager: ServiceManager = get()

    private val engine: CyclicEngineService = get()

    val packetRepository: PacketRepository = get()

    val world: World = get()

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

                single { JDBCPoolingService() }

                single { LoginWorkerSchedule() }

                single { LoginWorkerService() }

                single { CyclicDelaySchedule() }

                single { CyclicEngineService() }

                single { PacketService() }

                single {
                    World.newBuilder().setMembers(true).setOrigin(WorldOrigin.UNITED_STATES)
                        .setAccess(WorldAccess.DEVELOPMENT).setActivity(WorldActivity.NONE).build()
                }

                single {
                    GsonBuilder().registerTypeAdapter(Item::class.java, ItemAdapter()).create()
                }

                single {

                    val fileStore: VirtualFileStore = get()

                    val huffmanFile =
                        fileStore.fetchIndex(IndexConfig.BINARY.identifier).fetchNamedArchive("huffman")!!.fetchEntry(0)

                    HuffmanCodec(huffmanFile.fetchBuffer(true).toArray())

                }

                //todo move the parsing.
                single { PacketRepository() }

                single {
                    ServiceManager(
                        listOf(
                            get<JDBCPoolingService>(),
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

                single {

                    val engine: CyclicEngineService = get()

                    //ExecutorCoroutineDispatcher

                    engine.fetchExecutor().asCoroutineDispatcher()

                }

                single { ActorScope() }

            }

            val pathFindModule = module {
                single { DirectSearch() }
                single { AxisAlignment() }
                single { BreadthFirstSearch() }
                single { PathFinder(get(), get(), get(), get()) }
            }

            val collisionModule = module {
                single { Collisions() }
                //single { CollisionReader(get()) }
            }

            runBlocking {

                startKoin {

                    modules(
                        listOf(
                            mod,
                            pathFindModule,
                            collisionModule
                        )
                    )

                    val test = Test()
                    
                    RSPluginManager.apply {
                        Repository.definitionRepository = definition()
                        loadPlugins()
                        startPlugins()
                    }

                    test.engine.schedule(PreUpdatePlayerSynchronizationTask, true)

                    test.engine.schedule(PlayerWalkingTask, true)

                    test.engine.schedule(UpdatePlayerSynchronizationEvent, true)

                    test.engine.schedule(UpdateNpcSynchronizationTask, true)

                    test.serviceManager
                        .startAsync()
                        .awaitHealthy()

                    System.gc()

                }
            }

        }

    }

}